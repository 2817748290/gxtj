package com.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 14:43 2018/3/6
 */
public class getHtml {
    public static String getContent(String url) {
        Document doc = null;
        String content = "";
        try {
            doc = Jsoup.connect(url).get();
            if(url.contains("mp.weixin.qq.com")){//如果是微信公众号的文章
                Elements result = doc.select("#js_content");

//                for(int i=0;i<result.size();i++){
//                    content += "<p>"+result.eq(i).html()+"</p>";
//                }
                content = result.html();
                content = content.replace("data-src", "src");//将属性data-src替换为src，否则图片不能正常显示
//                content = content.replace("https://mmbiz.qpic.cn", "http://read.html5.qq.com/image?src=forum&amp;q=5&amp;r=0&amp;imgflag=7&amp;imageUrl=https://mmbiz.qpic.cn");
                //content = result.html();
            }else if(url.contains("tmtpost.com")){//如果是钛媒体的文章
                Elements result = doc.select(".post-container article .inner p");
                for(int i=0;i<result.size();i++){
                    content += "<p>"+result.eq(i).html()+"</p>";
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static String getPublishDate(String url){
        Document doc = null;
        String publishDate = "";
        try {
            doc = Jsoup.connect(url).get();
            if(url.contains("mp.weixin.qq.com")){//如果是微信公众号的文章
                Elements result = doc.select("#post-date");

                publishDate = result.text();
            }else if(url.contains("tmtpost.com")){//如果是钛媒体的文章
                Elements result = doc.select(".post-info .time");
                publishDate = result.text();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return publishDate;
    }
    public static String getAuthor(String url){
        Document doc = null;
        String author = "";
        try {
            doc = Jsoup.connect(url).get();
            if(url.contains("mp.weixin.qq.com")){//如果是微信公众号的文章
//                Elements result = doc.select("#meta_content").select("em").eq(1);
                Elements result = doc.select("#meta_content #post-user");

                author= result.text();
            }else if(url.contains("tmtpost.com")){//如果是钛媒体的文章
                Elements result = doc.select(".post-info .color-orange");
                author = result.text();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return author;
    }
    public static String getOnlyText(String url){
        Document doc = null;
        String content = "";
        try {
            doc = Jsoup.connect(url).get();
            if(url.contains("mp.weixin.qq.com")){//如果是微信公众号的文章
                Elements result = doc.select("#js_content p");
                content = result.text();
            }else if(url.contains("tmtpost.com")){//如果是钛媒体的文章
                Elements result = doc.select(".post-container article .inner p");
                content = result.text();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

        public static void main(String[] args) {
//        String s = getPublishDate("http://www.tmtpost.com/3112773.html");
//        String s = getPublishDate("https://mp.weixin.qq.com/s?__biz=MjM5NTU4NjY4MA==&mid=2651926061&idx=2&sn=5362dc42ffcd0f69a7a5edb2dca0661f&scene=0#wechat_redirect&sourceid=chrome&ie=UTF-8");
//
//        System.out.println(s);
//        DateUtil.dateFormat(s);
//        String s = getAuthor("http://www.tmtpost.com/3112773.html");
//        String s = getAuthor("http://mp.weixin.qq.com/s?__biz=MzA4NTIzNTU5Mw==&mid=2650770514&idx=1&sn=9edab391ba96f1f0627bde1e21bf1326&scene=0#wechat_redirect");
//        System.out.println(s);
//        getContent("https://mp.weixin.qq.com/s?__biz=MjM5MDIyMTc2MA==&mid=2655422060&idx=3&sn=dc371b72a58632bfedb0e47a41ba8de8&scene=0#wechat_redirect");
    }
//    public static void main(String[] args) {
    //http://www.tmtpost.com/3112773.html
//        String url = "https://mp.weixin.qq.com/s?__biz=MjM5NTU4NjY4MA==&mid=2651926061&idx=2&sn=5362dc42ffcd0f69a7a5edb2dca0661f&scene=0#wechat_redirect&sourceid=chrome&ie=UTF-8";
//        Document doc = null;
//        String content = "";
//        try {
//            doc = Jsoup.connect(url).get();
//            Elements result = doc.select("#js_content");//将属性data-src替换为src，否则图片不能正常显示
//            String alterContent = result.html().replace("data-src", "src");
//            System.out.println(alterContent);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
