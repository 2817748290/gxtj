package com.zhoulin.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.spider.convert.InfoConvert;
import com.spider.convert.InformationConvert;
import com.spider.getHtml;
import com.spider.util.DateUtil;
import com.textrank.TextRankKeyword;
import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.mapper.*;
import com.zhoulin.demo.service.InfoService;
import com.zhoulin.demo.service.InformationService;
import com.zhoulin.demo.service.JcsegService;
import com.zhoulin.demo.service.SpiderService;
import com.zhoulin.demo.service.search.SearchService;
import com.zhoulin.demo.utils.CheckType;
import com.zhoulin.demo.utils.TokenizerAnalyzerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * New实时爬虫模块
 *
 *
 *
 */
@Component
public class NewSpiderServiceImpl implements SpiderService{

    @Autowired
    private CheckType checkType;

    @Autowired
    private SearchService searchService;

    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private InformationService informationService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private InfoContentMapper infoContentMapper;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private InfoImageMapper infoImageMapper;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private JcsegService jcsegService;

    private static final Logger logger = LoggerFactory.getLogger(ModServiceImpl.class);

    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    public static final String CORPUS_FOLDER = "data/test/搜狗文本分类语料库迷你版";
    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "D:\\Java\\generator\\gxtj\\data\\test\\classification-model.ser";

    @Override
//    @Scheduled(fixedRate = 10800000)
    public void run() {
        for(int j=0;j<=10;j++) {

            //查询Ip信息的接口，返回json
            String baseUrl = "http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368=&page=" + j;

            String result = "";
            BufferedReader in = null;
            Long infoId = 0L;
            try {
                String urlNameString = baseUrl;
                URL realUrl = new URL(urlNameString);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();

                // 建立实际的连接
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                System.out.println("发送GET请求出现异常！" + e);
                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                Integer status = 0;
                Integer maxNum = 0;
                boolean sentence = false;
                List<String> tokenizerResult = new ArrayList<>();
                String analyzeContent = "";
                List<String> keywords = new ArrayList<>();
                //得到的json数据
                //System.out.println(result);
                //解析,
                JSONObject jsonObj = JSON.parseObject(result);
                //得到资讯数组
                JSONArray arr = jsonObj.getJSONObject("data").getJSONArray("list");
                String keyword = "";
                List<String> kws = new ArrayList<>();
                List<Type> typeList = new ArrayList<>();

                typeList = typeMapper.findAll();
                /*分类*/

                //初始化朴素贝叶斯
                IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());

                for (int i = 0; i < arr.size(); i++) {
                    List<Integer> matchList = new ArrayList<>();
                    JSONObject object = (JSONObject) arr.get(i);
                    Information information = new Information();
                    Info info = new Info();
                    TypeRelation typeRelation = new TypeRelation();
                    InfoContent infoContent = new InfoContent();
                    InfoImage infoImage = new InfoImage();
                    String onlyText = getHtml.getOnlyText(object.getString("source_url"));
                    String content = getHtml.getContent(object.getString("source_url"));
                    String images = object.getString("image_list");
                    InformationConvert.convert(object, information);
                    InfoConvert.convert(object, info);

                    int count = infoMapper.getCountByTitle(information.getTitle());
                    if(count>0){
                        logger.info(information.getTitle() + " >>>> 新闻重复不执行事务操作！");
                    }else{
                    //拼接要分析的文本
                    analyzeContent = information.getTitle() + information.getDescription() + onlyText;

                    //textRank提取关键词 8 个
//                keywords = new TextRankKeyword().getKeyword("", analyzeContent);
                    keywords = jcsegService.getKeywordsphrase(analyzeContent);
                    logger.info("关键词 >>>>" + keywords.toString());

                    //HanLP提取摘要
                    List<String> sentenceList = HanLP.extractSummary(analyzeContent, 3);
                    String summary = sentenceList.toString();
                    logger.info("提取文章摘要>>>>" + summary);

                    for (String kw : keywords) {
                        keyword = keyword + kw + ",";
                    }

//                tokenizerResult = TokenizerAnalyzerUtils.getAnalyzerResult(keyword);
//                for (String kw : tokenizerResult) {
//                    result = keyword + kw + ",";
//                }
                    information.setKeyword(keyword);
                    info.setKeyword(keyword);
                    keyword = "";
//                logger.info("获取的关键词为 >>>>> " + tokenizerResult);

                    //插入mysql
                    informationMapper.addInformation(information);

                    infoId = information.getId();

                    info.setInfoId(infoId);
                    String[] imageList = images.split(",");
                    if (imageList.length > 0) {
                        infoImage.setImage(imageList[0]);
                    }
                    infoImage.setInfoId(infoId);
                    infoContent.setInfoId(infoId);
                    infoContent.setContent(content);
                    typeRelation.setInfoId(infoId);
                    typeRelation.setOnlyText(onlyText);
                    typeRelation.setPublishDate(DateUtil.dateFormat(getHtml.getPublishDate(object.getString("source_url"))));
                    /*插入数据库*/
                    infoContentMapper.addInfoContent(infoContent);
                    infoMapper.addInfo(info);
                    infoImageMapper.addInfoImage(infoImage);


                    //分类
                    String typeName = classifier.classify(summary);
                    logger.info("获取的资讯类型为 >>>>> " + typeName);

                    int typeId = checkType.getTypeIdByTypeName(typeName);
                    typeRelation.setTypeId(typeId);
                    typeRelationMapper.addTypeRelation(typeRelation);

                    //插入es
                    searchService.indexPro(infoId);
                    logger.info(information.getTitle() + " >>>>> 新闻已执行事务操作！");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static NaiveBayesModel trainOrLoadModel() throws IOException
    {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }


}

