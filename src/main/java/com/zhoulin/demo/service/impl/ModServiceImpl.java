package com.zhoulin.demo.service.impl;

import com.ansj.vec.Word2VEC;
import com.google.common.primitives.Longs;
import com.textrank.TextRankKeyword;
import com.textrank.TextRankSummary;
import com.zhoulin.demo.bean.Info;
import com.zhoulin.demo.bean.InfoSort;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.TypeRelation;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;
import com.zhoulin.demo.mapper.TypeRelationMapper;
import com.zhoulin.demo.service.InfoService;
import com.zhoulin.demo.service.InformationService;
import com.zhoulin.demo.service.ModService;
import com.zhoulin.demo.service.search.InformationIndexKey;
import com.zhoulin.demo.utils.TokenizerAnalyzerUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型分析
 */
@Component
public class ModServiceImpl implements ModService{

    private static final Logger logger = LoggerFactory.getLogger(ModServiceImpl.class);

    private static final String INDEX_NAME = "information";

    private static final String INDEX_TYPE = "information";

    //kafka监听的主题
    private static final String INDEX_TOPIC = "information";

    @Autowired
    private TransportClient esClient;

    @Autowired
    private InformationService informationService;

    @Autowired
    private TypeRelationMapper relationMapper;

    @Autowired
    private InfoService infoService;


    /**
     * ElasticSearch全文本检索
     * 针对关键词的进行 描述 标题 内容 进行全域检索
     * @param infoSearch
     * @return json
     */
    @Override
    public ServiceMultiResult<Long> queryMuti(InfoSearch infoSearch) {

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.multiMatchQuery(infoSearch.getMutiContent(), "description","title","content").type("best_fields").operator(Operator.OR))
                .setExplain(true)
                .setSize(100)
                .setFetchSource(InformationIndexKey.ID, null)
                ;
        logger.info("！！！" + requestBuilder);

        logger.debug(requestBuilder.toString());

        List<String> infoList = new ArrayList<>();

        List<Long> infoIds = new ArrayList<>();

        SearchResponse searchResponse = requestBuilder.get();

        if(searchResponse.status() != RestStatus.OK){
            logger.warn("Search status is no ok for " + requestBuilder);
            return new ServiceMultiResult<>(0, infoIds);
        }

        for (SearchHit hit : searchResponse.getHits()) {
            logger.debug(String.valueOf(hit.getSource()));
            infoIds.add(Longs.tryParse(String.valueOf(hit.getSource().get(InformationIndexKey.ID))));
//            logger.info("详细信息" + hit.getSourceAsString());
//            infoList.add(hit.getSourceAsString());
        }



//        return new ServiceMultiResult<String>(searchResponse.getHits().totalHits, infoList);
        return new ServiceMultiResult<Long>(searchResponse.getHits().totalHits, infoIds);

    }

    @Override
    public ServiceMultiResult<Long> queryMutiAndTypeName(InfoSearch infoSearch) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(QueryBuilders.multiMatchQuery(infoSearch.getMutiContent(), "description","title","content").type("best_fields").operator(Operator.OR).boost(2))
                 .must(QueryBuilders.matchPhraseQuery("typeName", infoSearch.getTypeName()).boost(1));

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setExplain(true)
                .setSize(100)
                .setFetchSource(InformationIndexKey.ID, null)
                ;
        logger.info("！！！" + requestBuilder);

        logger.debug(requestBuilder.toString());

        List<String> infoList = new ArrayList<>();

        List<Long> infoIds = new ArrayList<>();

        SearchResponse searchResponse = requestBuilder.get();

        if(searchResponse.status() != RestStatus.OK){
            logger.warn("Search status is no ok for " + requestBuilder);
            return new ServiceMultiResult<>(0, infoIds);
        }

        for (SearchHit hit : searchResponse.getHits()) {
            logger.debug(String.valueOf(hit.getSource()));
            infoIds.add(Longs.tryParse(String.valueOf(hit.getSource().get(InformationIndexKey.ID))));
//            logger.info("详细信息" + hit.getSourceAsString());
//            infoList.add(hit.getSourceAsString());
        }



//        return new ServiceMultiResult<String>(searchResponse.getHits().totalHits, infoList);
        return new ServiceMultiResult<Long>(searchResponse.getHits().totalHits, infoIds);
    }

    @Override
    public ServiceMultiResult<Long> queryTypeName(InfoSearch infoSearch) {

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhraseQuery("typeName", infoSearch.getTypeName()))
                .setExplain(true)
                .setSize(100)
                .addSort("publishDate", SortOrder.DESC)
                .setFetchSource(InformationIndexKey.ID, null)
                ;
        logger.info("！！！" + requestBuilder);

        logger.debug(requestBuilder.toString());

        List<String> infoList = new ArrayList<>();

        List<Long> infoIds = new ArrayList<>();

        SearchResponse searchResponse = requestBuilder.get();

        if(searchResponse.status() != RestStatus.OK){
            logger.warn("Search status is no ok for " + requestBuilder);
            return new ServiceMultiResult<>(0, infoIds);
        }

        for (SearchHit hit : searchResponse.getHits()) {
            logger.debug(String.valueOf(hit.getSource()));
            infoIds.add(Longs.tryParse(String.valueOf(hit.getSource().get(InformationIndexKey.ID))));
//            logger.info("详细信息" + hit.getSourceAsString());
//            infoList.add(hit.getSourceAsString());
        }

//        return new ServiceMultiResult<String>(searchResponse.getHits().totalHits, infoList);
        return new ServiceMultiResult<Long>(searchResponse.getHits().totalHits, infoIds);
    }


}
