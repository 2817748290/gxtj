package com.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.convert.InfoConvert;
import com.spider.convert.InformationConvert;
import com.spider.util.DateUtil;
import com.zhoulin.demo.bean.*;
import com.zhoulin.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@Component
public class SpiderUtil {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private InformationMapper informationMapper;
    @Autowired
    private InfoContentMapper infoContentMapper;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private InfoImageMapper infoImageMapper;
    @Autowired
    private TypeRelationMapper typeRelationMapper;
    public void run(int page){
        //查询Ip信息的接口，返回json
//        String url="https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=2.24.1.1&resource_id=6006&format=json";

        String baseUrl="http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368=&page="+page;

        String result = "";
        BufferedReader in = null;
        Integer infoId = 0;
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
                    connection.getInputStream(),"UTF-8"));
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
        //得到的json数据
        //System.out.println(result);
        //解析,
        JSONObject jsonObj = JSON.parseObject(result);
        //得到资讯数组
        JSONArray arr =  jsonObj.getJSONObject("data").getJSONArray("list");
        for(int i = 0; i<arr.size(); i++){
            JSONObject object = (JSONObject) arr.get(i);
            Information information = new Information();
            Info info = new Info();
            TypeRelation typeRelation = new TypeRelation();
            InfoContent infoContent = new InfoContent();
            InfoImage infoImage = new InfoImage();
            InformationConvert.convert(object, information);
            InfoConvert.convert(object, info);
            System.out.println(information);
            try {
                int count = infoMapper.getCountByTitle(information.getTitle());
                if(count>0){
                    System.out.println("!!!   重复   !!!");
                }
                infoId = informationMapper.addInformation(information);
                info.setInfoId(infoId);
                String onlyText = getHtml.getOnlyText(object.getString("source_url"));
                String content = getHtml.getContent(object.getString("source_url"));
                String images = object.getString("image_list");
                String[] imageList = images.split(",");
                if(imageList.length>0){
                    infoImage.setImage(imageList[0]);
                }
                infoImage.setInfoId(infoId);
                infoContent.setInfoId(infoId);
                infoContent.setContent(content);
                typeRelation.setInfoId(infoId);
                typeRelation.setOnlyText(onlyText);
                typeRelation.setPublishDate(DateUtil.dateFormat(getHtml.getPublishDate(object.getString("source_url"))));
                infoContentMapper.addInfoContent(infoContent);
                infoMapper.addInfo(info);
                infoImageMapper.addInfoImage(infoImage);
                typeRelationMapper.addTypeRelation(typeRelation);
                System.out.println(infoId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void findAll(){
        try {
            List<UserInfo> informationList = userInfoMapper.getAllUserInfo();
            System.out.println("!!!!" + informationList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
