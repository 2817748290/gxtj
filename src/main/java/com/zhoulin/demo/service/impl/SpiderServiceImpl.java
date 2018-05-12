////package com.zhoulin.demo.service.impl;
////
////import com.alibaba.fastjson.JSON;
////import com.alibaba.fastjson.JSONArray;
////import com.alibaba.fastjson.JSONObject;
////import com.ansj.vec.Word2VEC;
////import com.spider.convert.InfoConvert;
////import com.spider.convert.InformationConvert;
////import com.spider.getHtml;
////import com.spider.util.DateUtil;
////import com.textrank.TextRankKeyword;
////import com.zhoulin.demo.bean.*;
////import com.zhoulin.demo.mapper.*;
////import com.zhoulin.demo.service.InfoService;
////import com.zhoulin.demo.service.InformationService;
////import com.zhoulin.demo.service.JcsegService;
////import com.zhoulin.demo.service.SpiderService;
////import com.zhoulin.demo.service.search.SearchService;
////import com.zhoulin.demo.utils.TokenizerAnalyzerUtils;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////
////import java.io.BufferedReader;
////import java.io.InputStreamReader;
////import java.net.URL;
////import java.net.URLConnection;
////import java.util.ArrayList;
////import java.util.Collections;
////import java.util.List;
////import java.util.Map;
////
/////**
//// * 实时爬虫模块
//// */
////@Component
////public class SpiderServiceImpl implements SpiderService{
////
////    @Autowired
////    private SearchService searchService;
////
////    @Autowired
////    private InformationMapper informationMapper;
////
////    @Autowired
////    private InformationService informationService;
////
////    @Autowired
////    private InfoService infoService;
////
////    @Autowired
////    private InfoContentMapper infoContentMapper;
////
////    @Autowired
////    private InfoMapper infoMapper;
////
////    @Autowired
////    private TypeMapper typeMapper;
////
////    @Autowired
////    private InfoImageMapper infoImageMapper;
////
////    @Autowired
////    private TypeRelationMapper typeRelationMapper;
////
////    @Autowired
////    private JcsegService jcsegService;
////
////    private static final Logger logger = LoggerFactory.getLogger(ModServiceImpl.class);
////
////    @Override
////    public void run(int page) {
////        //查询Ip信息的接口，返回json
//////        String url="https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=2.24.1.1&resource_id=6006&format=json";
////
////        String baseUrl="http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368=&page="+page;
////
////        String result = "";
////        BufferedReader in = null;
////        Long infoId = 0L;
////        try {
////            String urlNameString = baseUrl;
////            URL realUrl = new URL(urlNameString);
////            // 打开和URL之间的连接
////            URLConnection connection = realUrl.openConnection();
////
////            // 建立实际的连接
////            connection.connect();
////            // 获取所有响应头字段
////            Map<String, List<String>> map = connection.getHeaderFields();
////            // 定义 BufferedReader输入流来读取URL的响应
////            in = new BufferedReader(new InputStreamReader(
////                    connection.getInputStream(),"UTF-8"));
////            String line;
////            while ((line = in.readLine()) != null) {
////                result += line;
////            }
////        } catch (Exception e) {
////            System.out.println("发送GET请求出现异常！" + e);
////            e.printStackTrace();
////        }
////        // 使用finally块来关闭输入流
////        finally {
////            try {
////                if (in != null) {
////                    in.close();
////                }
////            } catch (Exception e2) {
////                e2.printStackTrace();
////            }
////        }
////        try{
////            Integer status = 0;
////            Integer maxNum = 0;
////            boolean sentence = false;
//////            List<String> tokenizerResult = new ArrayList<>();
////            String analyzeContent = "";
////            List<String> keywords = new ArrayList<>();
////            //得到的json数据
////            //System.out.println(result);
////            //解析,
////            JSONObject jsonObj = JSON.parseObject(result);
////            //得到资讯数组
////            JSONArray arr =  jsonObj.getJSONObject("data").getJSONArray("list");
////            String keyword = "";
////            List<String> kws = new ArrayList<>();
////            List<Type> typeList = new ArrayList<>();
////            Word2VEC vec = new Word2VEC();
////
////            typeList = typeMapper.findAll();
////            /*分类*/
////
////            //加载训练模型
////            vec.loadJavaModel("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\mod\\vector44.mod");
////            logger.info("!!! 加载训练模型完成 ");
////
////            for(int i = 0; i<arr.size(); i++) {
////                List<Integer> matchList = new ArrayList<>();
////                JSONObject object = (JSONObject) arr.get(i);
////                Information information = new Information();
////                Info info = new Info();
////                TypeRelation typeRelation = new TypeRelation();
////                InfoContent infoContent = new InfoContent();
////                InfoImage infoImage = new InfoImage();
////                String onlyText = getHtml.getOnlyText(object.getString("source_url"));
////                String content = getHtml.getContent(object.getString("source_url"));
////                String images = object.getString("image_list");
////                InformationConvert.convert(object, information);
////                InfoConvert.convert(object, info);
////                //拼接要分析的文本
////                analyzeContent = onlyText + information.getTitle() + information.getDescription();
////                //得到关键词
////                keywords = new TextRankKeyword().getKeyword("", analyzeContent);
//////                keywords = jcsegService.getKeywordsphrase(analyzeContent);
////                logger.info("!!!!" + keywords);
////
////                for (String kw : keywords) {
////                    keyword = keyword + kw + ",";
////                }
////                //过滤得到关键词
////                List<String> tokenizerResult = TokenizerAnalyzerUtils.getAnalyzerResult(keyword);
////                String rsKeywords = "";
////                for (String kw : tokenizerResult) {
////                    rsKeywords = rsKeywords + kw + ",";
////                }
////                information.setKeyword(rsKeywords);
////                info.setKeyword(rsKeywords);
//////                keyword = "";
////                logger.info("获取的关键词为 >>>>> " + rsKeywords);
////
////                //插入mysql
////                informationMapper.addInformation(information);
////
////                infoId = information.getId();
////
////                info.setInfoId(infoId);
////                String[] imageList = images.split(",");
////                if (imageList.length > 0) {
////                    infoImage.setImage(imageList[0]);
////                }
////                infoImage.setInfoId(infoId);
////                infoContent.setInfoId(infoId);
////                infoContent.setContent(content);
////                typeRelation.setInfoId(infoId);
////                typeRelation.setOnlyText(onlyText);
////                typeRelation.setPublishDate(DateUtil.dateFormat(getHtml.getPublishDate(object.getString("source_url"))));
////                /*插入数据库*/
////                infoContentMapper.addInfoContent(infoContent);
////                infoMapper.addInfo(info);
////                infoImageMapper.addInfoImage(infoImage);
////                typeRelationMapper.addTypeRelation(typeRelation);
////
////                //关键词存储到mysql
////    //                informationService.updateInformation(information);
////    //                infoService.updateInfo(info);
////                for (Type type : typeList) {
////                    Integer matchNum = 0;
////                    kws = vec.distance(type.getTypeName());
////                    logger.info("!!! 向量词 " + kws.get(0));
////                    logger.info("!!! 类型 " + type.getTypeName());
////
//////                    List<String> s = TokenizerAnalyzerUtils.getAnalyzerResult(information.getKeyword());
////                    //得到类型 关键词范围
////                    for (String kwT : kws) {
////                        for (String tan:tokenizerResult) {
//////                            if (kwT.contains(tan)) {
////                            if (tan.contains(kwT)) {
////                            matchNum = matchNum + 1;
////                        }
////                        }
////                    }
////                    logger.info("!!! 匹配成功 " + matchNum);
////                    matchList.add(matchNum);
////
////                }
//////                logger.info("daxiiao" + matchList.size());
////
////                maxNum = Collections.max(matchList);
////
////                for (int j = 0; j < matchList.size(); j++) {
////                    if (matchList.get(j).equals(maxNum)) {
////                        //插入
////                        typeRelation.setTypeId(j + 1);
////                        status = typeRelationMapper.updateTypeRelationByInfoId(typeRelation);
////                    }
////                }
////
////                logger.info("最终匹配类型为" + typeRelation.getTypeId());
////                //插入es
////                searchService.indexPro(infoId);
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////    }
////}
//package com.zhoulin.demo.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ansj.vec.Word2VEC;
//import com.spider.convert.InfoConvert;
//import com.spider.convert.InformationConvert;
//import com.spider.getHtml;
//import com.spider.util.DateUtil;
//import com.textrank.TextRankKeyword;
//import com.zhoulin.demo.bean.*;
//import com.zhoulin.demo.mapper.*;
//import com.zhoulin.demo.service.InfoService;
//import com.zhoulin.demo.service.InformationService;
//import com.zhoulin.demo.service.JcsegService;
//import com.zhoulin.demo.service.SpiderService;
//import com.zhoulin.demo.service.search.SearchService;
//import com.zhoulin.demo.utils.TokenizerAnalyzerUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
///**
// * 实时爬虫模块
// */
//@Component
//public class SpiderServiceImpl implements SpiderService{
//
//    @Autowired
//    private SearchService searchService;
//    @Autowired
//    private InformationMapper informationMapper;
//    @Autowired
//    private InformationService informationService;
//    @Autowired
//    private InfoService infoService;
//    @Autowired
//    private InfoContentMapper infoContentMapper;
//    @Autowired
//    private InfoMapper infoMapper;
//    @Autowired
//    private TypeMapper typeMapper;
//    @Autowired
//    private InfoImageMapper infoImageMapper;
//    @Autowired
//    private TypeRelationMapper typeRelationMapper;
//
//    @Autowired
//    private JcsegService jcsegService;
//
//    private static final Logger logger = LoggerFactory.getLogger(ModServiceImpl.class);
//
//    @Override
//    public void run(int page) {
//        //查询Ip信息的接口，返回json
////        String url="https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=2.24.1.1&resource_id=6006&format=json";
//
//        String baseUrl="http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368=&page="+page;
//
//        String result = "";
//        BufferedReader in = null;
//        Long infoId = 0L;
//        try {
//            String urlNameString = baseUrl;
//            URL realUrl = new URL(urlNameString);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//
//            // 建立实际的连接
//            connection.connect();
//            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream(),"UTF-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            System.out.println("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        try{
//            Integer status = 0;
//            Integer maxNum = 0;
//            boolean sentence = false;
//            List<String> tokenizerResult = new ArrayList<>();
//            String analyzeContent = "";
//            List<String> keywords = new ArrayList<>();
//            //得到的json数据
//            //System.out.println(result);
//            //解析,
//            JSONObject jsonObj = JSON.parseObject(result);
//            //得到资讯数组
//            JSONArray arr =  jsonObj.getJSONObject("data").getJSONArray("list");
//            String keyword = "";
//            List<String> kws = new ArrayList<>();
//            List<Type> typeList = new ArrayList<>();
//            Word2VEC vec = new Word2VEC();
//
//            typeList = typeMapper.findAll();
//            /*分类*/
//
//            //加载训练模型
////            vec.loadJavaModel("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\mod\\vector44.mod");
////            vec.loadGoogleModel("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\mod\\wiki_chinese_word2vec(Google).model");
//            vec.loadGoogleModel("D:\\Java\\generator\\gxtj\\src\\main\\resources\\library\\mod\\Google_word2vec_zhwiki1710_300d.bin");
//            logger.info("!!! 加载训练模型完成 ");
//
//            for(int i = 0; i<arr.size(); i++) {
//                List<Integer> matchList = new ArrayList<>();
//                JSONObject object = (JSONObject) arr.get(i);
//                Information information = new Information();
//                Info info = new Info();
//                TypeRelation typeRelation = new TypeRelation();
//                InfoContent infoContent = new InfoContent();
//                InfoImage infoImage = new InfoImage();
//                String onlyText = getHtml.getOnlyText(object.getString("source_url"));
//                String content = getHtml.getContent(object.getString("source_url"));
//                String images = object.getString("image_list");
//                InformationConvert.convert(object, information);
//                InfoConvert.convert(object, info);
//                //拼接要分析的文本
//                analyzeContent = information.getTitle() + information.getDescription();
//                keywords = new TextRankKeyword().getKeyword("", analyzeContent);
////                keywords = jcsegService.getKeywordsphrase(analyzeContent);
//
//                for (String kw : keywords) {
//                    keyword = keyword + kw + ",";
//                }
//
//                tokenizerResult = TokenizerAnalyzerUtils.getAnalyzerResult(keyword);
//                for (String kw : tokenizerResult) {
//                    result = keyword + kw + ",";
//                }
//                information.setKeyword(result);
//                info.setKeyword(result);
//                keyword = "";
//                logger.info("获取的关键词为 >>>>> " + tokenizerResult);
//
//                //插入mysql
//                informationMapper.addInformation(information);
//
//                infoId = information.getId();
//
//
//
//                info.setInfoId(infoId);
//                String[] imageList = images.split(",");
//                if (imageList.length > 0) {
//                    infoImage.setImage(imageList[0]);
//                }
//                infoImage.setInfoId(infoId);
//                infoContent.setInfoId(infoId);
//                infoContent.setContent(content);
//                typeRelation.setInfoId(infoId);
//                typeRelation.setOnlyText(onlyText);
//                typeRelation.setPublishDate(DateUtil.dateFormat(getHtml.getPublishDate(object.getString("source_url"))));
//                /*插入数据库*/
//                infoContentMapper.addInfoContent(infoContent);
//                infoMapper.addInfo(info);
//                infoImageMapper.addInfoImage(infoImage);
//                typeRelationMapper.addTypeRelation(typeRelation);
//
//                //插入es
////                searchService.indexPro(infoId);
//                //关键词存储到mysql
//                //                informationService.updateInformation(information);
//                //                infoService.updateInfo(info);
//                for (Type type : typeList) {
//                    Integer matchNum = 0;
//                    kws = vec.distance(type.getTypeName());
////                    logger.info("!!! 向量词 " + kws.get(0));
//                    logger.info("!!! 类型 " + type.getTypeName());
//
//                    List<String> s = TokenizerAnalyzerUtils.getAnalyzerResult(information.getKeyword());
////                    List<String> s = keywords;
//                    //得到类型 关键词范围
//                    for (String kwT : kws) {
////                        logger.info("!!! 遍历 " + kwT);
////                        if (s.contains(kwT)) {
////                            logger.info("!!! 匹配成功 " + kwT);
////                            matchNum = matchNum + 1;
////                        }
//                        for (String tan:s) {
//                            if (kwT.contains(tan)) {
//                                logger.info("!!! 匹配成功 " + kwT);
//                                matchNum = matchNum + 1;
//                            }
//                        }
//                    }
//                    logger.info("!!! 匹配成功 " + matchNum);
//                    matchList.add(matchNum);
//
//                }
//                logger.info("daxiiao" + matchList.size());
//
//                maxNum = Collections.max(matchList);
//
//                for (int j = 0; j < matchList.size(); j++) {
//                    if (matchList.get(j).equals(maxNum)) {
//                        //插入
//                        typeRelation.setTypeId(j + 1);
//                        logger.info("最终匹配类型为" + typeRelation.getTypeId());
//                        status = typeRelationMapper.updateTypeRelationByInfoId(typeRelation);
//                    }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
//
