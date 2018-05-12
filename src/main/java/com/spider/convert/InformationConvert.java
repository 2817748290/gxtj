package com.spider.convert;

import com.alibaba.fastjson.JSONObject;
import com.spider.getHtml;
import com.spider.util.DateUtil;
import com.zhoulin.demo.bean.Information;

import java.util.Date;

public class InformationConvert {
    public static void convert(JSONObject object, Information information){
        Date publishDate = DateUtil.dateFormat(getHtml.getPublishDate(object.getString("source_url")));
        String content = getHtml.getContent(object.getString("source_url"));
        String author = getHtml.getAuthor(object.getString("source_url"));
        information.setId(object.getLong("id"));
        information.setDescription(object.getString("description"));
        information.setAuthor(author);
        information.setLikes(object.getInteger("likes"));
        information.setReads(object.getInteger("reads"));
        information.setScore(object.getInteger("score"));
        information.setSourceSite(object.getString("source_site"));
        information.setSourceUrl(object.getString("source_url"));
        information.setTitle(object.getString("title"));
        information.setPublishDate(publishDate);
        information.setContent(content);
    }
}
