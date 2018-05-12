package com.spider.convert;

import com.alibaba.fastjson.JSONObject;
import com.spider.getHtml;
import com.spider.util.DateUtil;
import com.zhoulin.demo.bean.Info;

import java.util.Date;

public class InfoConvert {
    public static void convert(JSONObject object, Info info){
        Date publishDate = DateUtil.dateFormat(getHtml.getPublishDate(object.getString("source_url")));
        String author = getHtml.getAuthor(object.getString("source_url"));
        info.setId(object.getLong("id"));
        info.setDescription(object.getString("description"));
        info.setAuthor(author);
        info.setLikes(object.getInteger("likes"));
        info.setReads(object.getInteger("reads"));
        info.setScore(object.getInteger("score"));
        info.setSourceSite(object.getString("source_site"));
        info.setSourceUrl(object.getString("source_url"));
        info.setTitle(object.getString("title"));
        info.setPublishDate(publishDate);
    }
}
