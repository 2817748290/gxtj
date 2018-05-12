/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@hankcs.com</email>
 * <create-date>16/2/20 AM11:46</create-date>
 *
 * <copyright file="DemoAtFirstSight.java" company="码农场">
 * Copyright (c) 2008-2016, 码农场. All Right Reserved, http://www.hankcs.com/
 * This source is subject to Hankcs. Please contact Hankcs to get more information.
 * </copyright>
 */
package com.zhoulin.demo.hanLP;



import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;

import java.io.File;
import java.io.IOException;

/**
 * 第一个demo,演示文本分类最基本的调用方式
 *
 * @author hankcs
 */
public class DemoTextClassification
{
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    public static final String CORPUS_FOLDER = "data/test/搜狗文本分类语料库迷你版";
    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "data/test/classification-model2.ser";

    public static void main(String[] args) throws IOException
    {
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
//        predict(classifier, "C罗压梅西内马尔蝉联金球奖 2017=C罗年");
//        predict(classifier, "英国造航母耗时8年仍未服役 被中国速度远远甩在身后");
//        predict(classifier, "研究生考录模式亟待进一步专业化");
//        predict(classifier, "如果真想用食物解压,建议可以食用燕麦");
//        predict(classifier, "通用及其部分竞争对手目前正在考虑解决库存问题");
        predict(classifier, "新榜连续2年全国公众号总排名前5 欢迎转载，须署名并注明来自占豪微信，否则追究法律责任。  特朗普治国有多随意？4月9日美国美国政治类网络媒体Axios爆出猛料：之前特朗普在中国清明节假期突然宣布要再对1000亿美元中国产品加征关税的相关决定，根本没有与任何幕僚商量，而是自己突然心血来潮拍脑门的结果！  我勒个天！如果美国真的要再对中国追加1000亿美元的商品征税，那不仅仅意味着贸易战的升级，更意味着中美之间的贸易关系将会受到重创，中美贸易战的规模将立刻上升两个量级，非常危险！当今世界，中美关系是世界上最重要的双边关系。美俄斗成那样，世界都没有乱，但如果中美关系进一步因为特朗普的政策严重恶化，那么全世界都将受到严重威胁，这一切都是因为中国经济的规模太大了，中国经济的影响力太大了！  然而，这么大的事，特朗普和任何人都没商量，直接就宣布了！  这家媒体写道：   “白宫里没有召开过哪怕一次会议去探讨这个史上最狠的威胁会有什么好处和坏处，特朗普甚至都没有和他的首席经济顾问库德洛咨询过此事，就直接把他的计划以既定事实的口吻公布了……”  甚至，这件事连白宫幕僚长在事前都没有听说过。由此可见，特朗普施政的随意性和独断性，而这恐怕也是特朗普的重要幕僚一个个辞职的根本原因之一。而从最近的情况看，甚至连他的女儿、女婿都有些噤声了。接下来，相信特朗普的施政还将具有这样的问题，那么大概率还是会有幕僚不断从他的麾下辞职！  然而，现在的问题是，美国接下来怎么办？是继续公布一千亿美元的商品清单？还是对中国进行妥协，放下姿态与中国展开平等互利的谈判？当然，如果美国没有做好向中国出口高科技产品的准备，又有什么资格要求中国在关税上对美国予以照顾呢？  现在，被架在火上烤的是白宫，是特朗普！在没有台阶下的情况下，难道找叙利亚和普京的茬就是好的金蝉脱壳之计吗？  朗普：赶紧坐下吧号主，都被你说中我就没法混了！  点赞、分享，是最好的支持！  占豪系列作品签名版长按二维码订购  战友临走点下方留个手印。如觉文章不错，转发亲朋让大家看到咱的中国立场。 思考者在阅读，点击下面链接查阅 贸易战被中国怼懵了，美国要打击叙利亚普京下令反击！ 博鳌中国拱手迎客，却对美迎面一锤，留给特朗普的时间不多了！ 外交部狠怼美国一把又亮了！特朗普最新意图在推特曝光！ 特朗普突然发推谈贸易战，释放强烈信号！ 刚刚，美国软肋彻底暴露！这次中国要狠狠打，把特朗普打回谈判桌！ 特朗普再甩千亿，欧日也想捅刀中国？不惜一切代价，打退来犯之敌！ 惹毛雄起中国！特朗普，你这次真要“掉得大”！ 狠狠打！打出30年经济和平！中国直击美国要害！ 贸易战逼中俄联手！中东终于捂不住了，一夜大乱！ 中美贸易战，最倒霉的竟是他们，你绝对想不到！ 强力实锤反击，中国对美加关税！中国打赢这场贸易战，能得到什么，会损失什么？ 中美贸易战，原来有大秘密，中国为何必须打赢？ 中国放的这句狠话，美国再听不懂就要出大事了！ 中国反击又一重锤！人民币代替美元结算，真是拳拳到肉！ 神秘而重磅的访问，他向世界释放7大信号！（文末大福利） 辽宁舰与至少40艘舰南海军演，中国展示势将贸易战打到底的决心！ 中国这记重锤，让美国内伤！上海原油期货上市，意义重磅！ 中美贸易战，美国必败的3大原因在此！ 中国已反击美国！贸易战，中国５招制敌！ 中美贸易战背后，是特朗普中东大阴谋！ 特朗普炮轰中国，贸易战找错对象，白宫犯三大战略性错误！ ");
        predict(classifier, "中国已反击美国, 特朗普炮轰中国, 中国打赢这场贸易战");
//        predict(classifier, "彬彬今年10岁，     他从小开始学钢琴，     读小学之后也没有落下。                    最近，彬彬参加不少业余组钢琴比赛，都获了奖，这让彬彬爸爸十分开心。他的博士生同学见到也常常夸彬彬，不仅聪明，钢琴也弹得好。      每到这时候，彬彬爸就无比庆幸自己当初辞去高薪工作，");
//        predict(classifier, "导读       露脚踝的“时尚”吹进了校园。近日，河北一所中学通过官方微信发布《孩子，请放下你的裤腿》，组织老师检查督导，纠正学生们这种不惜损伤身体追逐“时尚”的做法。                 某校组织老师检查学生是否 露脚踝                 不知道在路上有没有遇到这样的穿");
//        predict(classifier, "面对近日酒店评级问题引发的争议，4月11日，携程CFO王肖璠作出了回应：竞价排名？不存在的。携程的排序主要依据算法。        “所有互联网企业能够看到的恶，在这家企业里都有。”          4月9日，知名媒体人王志安在其微信公众号上发文，呼吁网友   “趁你还没有被坑死，赶紧卸载携程吧”");
//        predict(classifier, "现在大部分女人已经明白，男人的劣根性超级顽劣，你不能一言不合就分手，应该好好打造一个舒适宜人的家，不然下一个没准比这个更糟糕。            有个女读者对男友很生气，忍不住发来一串牢骚。她是一名在家工作的设计师，有一天忙得半死，跟正在上班的男友撒娇说：“亲爱的，我好饿啊，下班回来给我做饭吧。");
//        predict(classifier, "临近五一     再度被票圈的日本樱花刷屏     蠢蠢欲动的少女心又在春天开始作祟                           （这个季节的日本是酱紫哒~）             记得我看《秒速五厘米》的时候正读初中，漫天飘零的樱花似乎就是浪漫和初恋的代名词。带着这份憧憬，去年我踏上前往  ");
//        predict(classifier, "新榜连续2年全国公众号总排名前5     欢迎转载，须署名并注明来自占豪微信，否则追究法律责任。       特朗普治国有多随意？4月9日美国美国政治类网络媒体Axios爆出猛料：之前特朗普在中国清明节假期突然宣布要再对1000亿美元中国产品加征关税的相关决定，根本没有与任何幕僚商量，而是自己突然心");
//        predict(classifier, "俗话说“好事多磨”，不仅指一个期待的实现会几经波折，近几年也经常被用在影视圈，形容一些开播/上映前历经磨难的优秀作品。          新闻称芒果台的新剧《远大前程》就是如此，由 陈思诚 担任监制和总编剧，主演除了他还有 佟丽娅、袁弘、郭采洁 ，  最早宣传的是去年底播出，接连两次延期，最终定档4");
    }

    private static void predict(IClassifier classifier, String text)
    {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
    }

    private static NaiveBayesModel trainOrLoadModel() throws IOException
    {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            System.err.println("没有文本分类语料");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}
