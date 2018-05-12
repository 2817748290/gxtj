package com.zhoulin.demo.service.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.Longs;
import com.zhoulin.demo.bean.InfoSort;
import com.zhoulin.demo.bean.Type;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.mapper.TypeMapper;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.service.InformationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.Highlighter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private static final String INDEX_NAME = "information";

    private static final String INDEX_TYPE = "information";

    //kafka监听的主题
    private static final String INDEX_TOPIC = "information";

    @Autowired
    private InformationService informationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TransportClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = INDEX_TOPIC)
    private void handleMessage(String content){
        try {
            //ObjectMapper: 将java对象转化成json格式
            InformationIndexMessage message = objectMapper.readValue(content, InformationIndexMessage.class);

            switch (message.getOperation()){
                case InformationIndexMessage.INDEX:
                    this.createOrUpdateIndex(message);
                    break;
                case InformationIndexMessage.REMOVE:
                    this.removeIndex(message);
                    break;
                default:
                    logger.warn("不支持处理该信息操作", content);
                    break;
            }

        } catch (IOException e) {
            logger.error("转化json格式失败", content, e);
        }
    }

    private void createOrUpdateIndex(InformationIndexMessage message){

        Long id = message.getInfoId();

        Information information = new Information();
        TypeRelation typeRelation = new TypeRelation();
        try {
            information = informationService.getInfoByInfoId(id);

            System.out.println(">>>>>"+information.getDescription());

            if(information==null){
                logger.error("无对应id的资讯", id);
                this.index(id, message.getRetry() + 1);
                return;
            }
            typeRelation = typeRelationMapper.getInfoByTRId(id);
            information.setContent(typeRelation.getOnlyText());
            InformationIndexTemplate indexTemplate = new InformationIndexTemplate();
            modelMapper.map(information, indexTemplate);

            SearchRequestBuilder requestBuilder = this.esClient
                    .prepareSearch(INDEX_NAME)
                    .setTypes(INDEX_TYPE)
                    .setQuery(QueryBuilders.termQuery(InformationIndexKey.ID, id));

            logger.debug(requestBuilder.toString());

            SearchResponse searchResponse = requestBuilder.get();

            boolean isSuccess;

            long totalHit = searchResponse.getHits().getTotalHits();

            if(totalHit == 0){
                isSuccess = create(indexTemplate);
            }else if(totalHit == 1){
                String esId = searchResponse.getHits().getAt(0).getId();
                isSuccess = update(esId, indexTemplate);
            }else {
                isSuccess = deleteAndCreate(totalHit, indexTemplate);
            }

            if (isSuccess) {
                logger.debug("执行成功的资讯" + id);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeIndex(InformationIndexMessage message){

        long id = message.getInfoId();

        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(InformationIndexKey.ID, id))
                .source(INDEX_NAME);

        logger.debug("Delete by query for info:" + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        logger.debug("Delete total: " + deleted);

        if(deleted<=0){
            this.remove(id, message.getRetry() + 1);
        }

    }

    @Override
    public void index(long id) {
        this.index(id, 0);
    }

    private void index(long id, Integer retry){
        if(retry > InformationIndexMessage.MAX_RETRY){
            logger.error("超过最大请求索引次数" + id );
            return;
        }

        InformationIndexMessage message = new InformationIndexMessage(id, InformationIndexMessage.INDEX, retry);

        try {
            kafkaTemplate.send(INDEX_TOPIC, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            logger.error("json转换错误 " + message, e);
        }

    }

    private boolean create(InformationIndexTemplate indexTemplate){

        try {
            IndexResponse response = this.esClient
                    .prepareIndex(INDEX_NAME, INDEX_TYPE)
                    .setSource(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON)
                    .get();

            logger.debug("添加资讯索引" + indexTemplate.getId());

            if (response.status() == RestStatus.CREATED){
                return true;
            } else {
                return false;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.debug("添加资讯索引异常！" + indexTemplate.getId(), e);
            return false;
        }

    }

    private boolean update(String esId, InformationIndexTemplate indexTemplate){

        try {
            UpdateResponse response = this.esClient
                    .prepareUpdate(INDEX_NAME, INDEX_TYPE, esId)
                    .setDoc(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON)
                    .get();

            logger.debug("更新资讯索引" + indexTemplate.getId());

            if (response.status() == RestStatus.OK){
                return true;
            } else {
                return false;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.debug("更新资讯索引异常！" + indexTemplate.getId(), e);
            return false;
        }

    }

    private boolean deleteAndCreate(long totalHit, InformationIndexTemplate indexTemplate){
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(InformationIndexKey.ID, indexTemplate.getId()))
                .source(INDEX_NAME);

        logger.debug("Delete by query for info:" + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if(deleted != totalHit){
            logger.warn("需要删除 {}, 但是实际删除 {}", totalHit, deleted);
            return false;
        }else {
            return create(indexTemplate);
        }

    }

    @Override
    public void remove(long id) {
        //retry 传递给kafka
        this.remove(id, 0);
    }

    @Override
    public ServiceMultiResult<Long> query(InfoSearch infoSearch) {
        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(
                QueryBuilders.termQuery(InformationIndexKey.TITLE, infoSearch.getTitleSearch())
        );

        //!"*".equals(xxx) 检索xxx不为null 防止敲空格
        if(infoSearch.getSourceSiteSearch() != null && !"*".equals(infoSearch.getSourceSiteSearch())){

            boolQueryBuilder.filter(
                    QueryBuilders.termQuery(InformationIndexKey.SOURCE_SITE, infoSearch.getSourceSiteSearch())
            );

        }

        if(infoSearch.getDescription() != null && !"*".equals(infoSearch.getDescription())){

            boolQueryBuilder.filter(
                    QueryBuilders.termQuery(InformationIndexKey.DESCRIPTION, infoSearch.getDescription())
            );

        }

        if(infoSearch.getContent() != null && !"*".equals(infoSearch.getContent())){

            boolQueryBuilder.filter(
                    QueryBuilders.termQuery(InformationIndexKey.CONTENT, infoSearch.getContent())
            );

        }

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQueryBuilder)
//                .setQuery(QueryBuilders.multiMatchQuery(infoSearch.getMutiContent(), "description","title","content"))
                .addSort(
                        InfoSort.getSortKey(infoSearch.getSort()),
                        SortOrder.fromString(infoSearch.getOrder())
                )
                .setFrom(infoSearch.getStart())
                .setSize(infoSearch.getSize())
                .setFetchSource(InformationIndexKey.ID, null);

        logger.debug(requestBuilder.toString());

        List<Long> infoIds = new ArrayList<>();

        SearchResponse searchResponse = requestBuilder.get();

        if(searchResponse.status() != RestStatus.OK){
            logger.warn("Search status is no ok for " + requestBuilder);
            return new ServiceMultiResult<>(0, infoIds);
        }

        for (SearchHit hit : searchResponse.getHits()) {
            logger.debug(String.valueOf(hit.getSource()));
            infoIds.add(Longs.tryParse(String.valueOf(hit.getSource().get(InformationIndexKey.ID))));
        }

        return new ServiceMultiResult<>(searchResponse.getHits().totalHits, infoIds);
    }

    @Override
    public boolean indexPro(long id) {
        Information information = new Information();
        TypeRelation typeRelation = new TypeRelation();
        try {
            information = informationService.getInfoByInfoId(id);
            if(information==null){
                logger.error("无对应id的资讯", id);
                return false;
            }
            typeRelation = typeRelationMapper.getInfoByTRId(id);
            Type type = typeMapper.getTypeByTypeId(typeRelation.getTypeId());
            information.setContent(typeRelation.getOnlyText());
            information.setTypeName(type.getTypeName());
            InformationIndexTemplate indexTemplate = new InformationIndexTemplate();
            modelMapper.map(information, indexTemplate);
            SearchRequestBuilder requestBuilder = this.esClient
                    .prepareSearch(INDEX_NAME)
                    .setTypes(INDEX_TYPE)
                    .setQuery(QueryBuilders.termQuery(InformationIndexKey.ID, id));
            logger.debug(requestBuilder.toString());

            SearchResponse searchResponse = requestBuilder.get();

            boolean isSuccess;

            long totalHit = searchResponse.getHits().getTotalHits();

            if(totalHit == 0){
                isSuccess = create(indexTemplate);
                }else if(totalHit == 1){
                String esId = searchResponse.getHits().getAt(0).getId();
                isSuccess = update(esId, indexTemplate);
                }else {
                isSuccess = deleteAndCreate(totalHit, indexTemplate);
                }

                if (isSuccess) {
                logger.debug("执行成功的资讯" + id);
                }
                return isSuccess;
            } catch (Exception e) {
            e.printStackTrace();
            return false;
            }
    }



    private void remove(long id, Integer retry){
        if(retry > InformationIndexMessage.MAX_RETRY){
            logger.error("超过最大请求索引次数" + id );
            return;
        }

        InformationIndexMessage message = new InformationIndexMessage(id, InformationIndexMessage.REMOVE, retry);

        try {
            //传递给kafka 使得kafka消费掉
            kafkaTemplate.send(INDEX_TOPIC, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            logger.error("json转换错误 " + message, e);
        }
    }

    /**
     * 搜索 关键词高亮显示
     * @param infoSearch
     * @return
     */
    @Override
    public ServiceMultiResult<Information> queryMultiMatch(InfoSearch infoSearch) {

        Information information = new Information();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String,Object>();

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.multiMatchQuery(infoSearch.getMutiContent(), "description","title","content").type("best_fields").operator(Operator.OR))
                .setExplain(true)
//                .setFetchSource(InformationIndexKey.ID, null)
                ;

        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);;

        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");

        //高亮回显
        requestBuilder.highlighter(highlightBuilder);

        logger.info("！！！" + requestBuilder);

        logger.debug(requestBuilder.toString());

        List<Information> infoList = new ArrayList<>();

        SearchResponse searchResponse = requestBuilder.get();

        if(searchResponse.status() != RestStatus.OK){
            logger.warn("Search status is no ok for " + requestBuilder);
            return new ServiceMultiResult<>(0, null);
        }

        for (SearchHit hit : searchResponse.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            //title高亮
            HighlightField titleField = highlightFields.get("title");
            try {
                information = objectMapper.readValue(hit.getSourceAsString(), Information.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(titleField!=null){
                Text[] fragments = titleField.fragments();
                String name = "";
                for (Text text : fragments) {
                    name+=text;
                }
                information.setTitle(name);
            }

            //describe高亮
            HighlightField describeField = highlightFields.get("description");
            if(describeField!=null){
                Text[] fragments = describeField.fragments();
                String describe = "";
                for (Text text : fragments) {
                    describe+=text;
                }
                information.setDescription(describe);
            }

            //describe高亮
            HighlightField contentField = highlightFields.get("content");
            if(contentField!=null){
                Text[] fragments = contentField.fragments();
                String content = "";
                for (Text text : fragments) {
                    content+=text;
                }
                information.setContent(content);
            }

            infoList.add(information);

            logger.debug(String.valueOf(hit.getSource()));

            logger.info("详细信息" + hit.getSourceAsString());

        }

        return new ServiceMultiResult<Information>(searchResponse.getHits().totalHits, infoList);
    }

}
