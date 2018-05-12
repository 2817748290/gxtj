package com.zhoulin.demo.utils;

import com.ansj.vec.constants.Constants;
import org.apache.commons.io.FileUtils;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackie on 2016/12/3 0003.
 */
public class TokenizerAnalyzerUtils {

    public TokenizerAnalyzerUtils(){

    }


    public static void main(String args[]) throws IOException {
        List<String> tokenizerResult = getAnalyzerResult("关于我们     黑龙江龙广律师事务所，是一家综合制律师事务所。龙广所目前为黑龙江省人民政府、招商银行哈尔滨分行及省内多家知名企业提供专业法律顾问服务、新三板等非诉法律服务；同时还面向社会公众提供优质诉讼业务服务。 联系方式：400-872-6900 律所地址：哈尔滨市赣水路30号地王大厦7层    综合来源：台州交通广播及网络        “那天我把车子停在路边，打开车门，一只脚已经下车，另一只脚还在车上的时候，听到‘砰’的很响一声，一辆电瓶车撞到了我的车门上。”周某是这样描述当时撞人的情景的，一开始，他有点蒙，“我觉得人不是我撞的，是她自己撞到我车门上的。”        浦江人周某因交通肇事罪，被判处有期徒刑一年，缓刑六个月。        2014年9月1日上午9点多，周某独自一人开车到城里办事，把自己的小型商务车停靠在了路边。准备下车前，他瞄了一眼后视镜，没看到后面有车辆。之后他解下安全带、将车子熄火、拔下汽车钥匙，打开了驾驶室侧门，把一只脚跨了出去。         可就在这停车到开车门不到20秒的时间里，悲剧发生了。才三十出头的吴女士骑着电瓶车同方向行驶，来不及躲避撞了上来，当场昏迷。 吴女士的电瓶车侧翻在地上，人却飞了出去。不幸的是，吴女士头部着地，虽然血流得不多，但是失去了意识。         一看吴女士摔得不轻，周某连忙想办法抢救吴女士，并让行人帮忙报了警。但经过十多天的抢救，吴女士还是走了。        经浦江县交警大队事故责任认定：周某驾驶机动车在道路上临时停车，开车门时未注意来往车辆，承担事故全部责任。        9月22日，周某与吴女士家属达成民事赔偿协议，赔偿了62万余元，并取得被害人家属的谅解。        浦江县人民法院审理认为，周某违反道路交通安全法，应当以交通肇事罪依法追究其刑事责任。因周某有自首情节，有明显悔罪表现，且取得了吴女士家属的谅解，可酌情从轻处罚并适用缓刑，故作出上述判决。 法律规定： 不管开车还是乘车，开车门都应注意！一定要看清周边路况！  提醒：停车后应\"二次开门\"，先将车门开拳头大小缝，经观察确认安全后再开门。告诉身边的人，千万别大意！ 微信号：lgls82143999 电话：400-872-6900 地址：哈尔滨市南岗区赣水路30号地王大厦7层");
        for (String kw:tokenizerResult) {
            System.out.println(kw);
        }
//        System.out.println(tokenizerResult);
    }

    public static List<String> getAnalyzerResult(String input) {
        List<String> stringList = new ArrayList<>();
        StringReader sr=new StringReader(input);
        IKSegmenter ik=new IKSegmenter(sr, true);//true is use smart
        Lexeme lex=null;
        List<String> stopWordsList = getStopWordsList();
//        StringBuilder stringBuilder = new StringBuilder();

        try {
            while((lex=ik.next())!=null){
                if(stopWordsList.contains(lex.getLexemeText())) {
                    continue;
                }
//                stringBuilder.append(lex.getLexemeText() + Constants.BLANKSPACE);

                    stringList.add(lex.getLexemeText());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to parse input content");
        }
//        return stringBuilder.toString();

        return stringList;
    }


    private static List<String> getStopWordsList(){
        List<String> stopWordList = null;
        File stopWordFile = new File(TokenizerAnalyzerUtils.class.getResource("/library/comment/stopwords.dic").getPath());
//          File stopWordFile = new File(TokenizerAnalyzerUtils.class.getResource("/library/stopwords/swresult_withoutnature.txt").getPath());
        try {
             stopWordList = FileUtils.readLines(stopWordFile, Constants.ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail to load stop word dictionary");
        }
        return stopWordList;
    }

}
