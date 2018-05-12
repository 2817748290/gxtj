package com.textrank;


import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;

import java.util.*;

/**
 * TextRank 自动摘要
 * @author hankcs
 */
public class TextRankSummary
{
    /**
     * 阻尼系数（ＤａｍｐｉｎｇＦａｃｔｏｒ），一般取值为0.85
     */
    final double d = 0.85f;
    /**
     * 最大迭代次数
     */
    final int max_iter = 200;
    final double min_diff = 0.001f;
    /**
     * 文档句子的个数
     */
    int D;
    /**
     * 拆分为[句子[单词]]形式的文档
     */
    List<List<String>> docs;
    /**
     * 排序后的最终结果 score <-> index
     */
    TreeMap<Double, Integer> top;

    /**
     * 句子和其他句子的相关程度
     */
    double[][] weight;
    /**
     * 该句子和其他句子相关程度之和
     */
    double[] weight_sum;
    /**
     * 迭代之后收敛的权重
     */
    double[] vertex;

    /**
     * BM25相似度
     */
    BM25 bm25;

    public TextRankSummary(List<List<String>> docs)
    {
        this.docs = docs;
        bm25 = new BM25(docs);
        D = docs.size();
        weight = new double[D][D];
        weight_sum = new double[D];
        vertex = new double[D];
        top = new TreeMap<Double, Integer>(Collections.reverseOrder());
        solve();
    }

    private void solve()
    {
        int cnt = 0;
        for (List<String> sentence : docs)
        {
            double[] scores = bm25.simAll(sentence);
//            System.out.println(Arrays.toString(scores));
            weight[cnt] = scores;
            weight_sum[cnt] = sum(scores) - scores[cnt]; // 减掉自己，自己跟自己肯定最相似
            vertex[cnt] = 1.0;
            ++cnt;
        }
        for (int _ = 0; _ < max_iter; ++_)
        {
            double[] m = new double[D];
            double max_diff = 0;
            for (int i = 0; i < D; ++i)
            {
                m[i] = 1 - d;
                for (int j = 0; j < D; ++j)
                {
                    if (j == i || weight_sum[j] == 0) continue;
                    m[i] += (d * weight[j][i] / weight_sum[j] * vertex[j]);
                }
                double diff = Math.abs(m[i] - vertex[i]);
                if (diff > max_diff)
                {
                    max_diff = diff;
                }
            }
            vertex = m;
            if (max_diff <= min_diff) break;
        }
        // 我们来排个序吧
        for (int i = 0; i < D; ++i)
        {
            top.put(vertex[i], i);
        }
    }

    /**
     * 获取前几个关键句子
     * @param size 要几个
     * @return 关键句子的下标
     */
    public int[] getTopSentence(int size)
    {
        Collection<Integer> values = top.values();
        size = Math.min(size, values.size());
        int[] indexArray = new int[size];
        Iterator<Integer> it = values.iterator();
        for (int i = 0; i < size; ++i)
        {
            indexArray[i] = it.next();
        }
        return indexArray;
    }

    /**
     * 简单的求和
     * @param array
     * @return
     */
    private static double sum(double[] array)
    {
        double total = 0;
        for (double v : array)
        {
            total += v;
        }
        return total;
    }

    public static void main(String[] args)
    {
        String document = "新榜连续2年全国公众号总排名前5 欢迎转载，须署名并注明来自占豪微信，否则追究法律责任。  特朗普治国有多随意？4月9日美国美国政治类网络媒体Axios爆出猛料：之前特朗普在中国清明节假期突然宣布要再对1000亿美元中国产品加征关税的相关决定，根本没有与任何幕僚商量，而是自己突然心血来潮拍脑门的结果！  我勒个天！如果美国真的要再对中国追加1000亿美元的商品征税，那不仅仅意味着贸易战的升级，更意味着中美之间的贸易关系将会受到重创，中美贸易战的规模将立刻上升两个量级，非常危险！当今世界，中美关系是世界上最重要的双边关系。美俄斗成那样，世界都没有乱，但如果中美关系进一步因为特朗普的政策严重恶化，那么全世界都将受到严重威胁，这一切都是因为中国经济的规模太大了，中国经济的影响力太大了！  然而，这么大的事，特朗普和任何人都没商量，直接就宣布了！  这家媒体写道：   “白宫里没有召开过哪怕一次会议去探讨这个史上最狠的威胁会有什么好处和坏处，特朗普甚至都没有和他的首席经济顾问库德洛咨询过此事，就直接把他的计划以既定事实的口吻公布了……”  甚至，这件事连白宫幕僚长在事前都没有听说过。由此可见，特朗普施政的随意性和独断性，而这恐怕也是特朗普的重要幕僚一个个辞职的根本原因之一。而从最近的情况看，甚至连他的女儿、女婿都有些噤声了。接下来，相信特朗普的施政还将具有这样的问题，那么大概率还是会有幕僚不断从他的麾下辞职！  然而，现在的问题是，美国接下来怎么办？是继续公布一千亿美元的商品清单？还是对中国进行妥协，放下姿态与中国展开平等互利的谈判？当然，如果美国没有做好向中国出口高科技产品的准备，又有什么资格要求中国在关税上对美国予以照顾呢？  现在，被架在火上烤的是白宫，是特朗普！在没有台阶下的情况下，难道找叙利亚和普京的茬就是好的金蝉脱壳之计吗？  朗普：赶紧坐下吧号主，都被你说中我就没法混了！  点赞、分享，是最好的支持！  占豪系列作品签名版长按二维码订购  战友临走点下方留个手印。如觉文章不错，转发亲朋让大家看到咱的中国立场。 思考者在阅读，点击下面链接查阅 贸易战被中国怼懵了，美国要打击叙利亚普京下令反击！ 博鳌中国拱手迎客，却对美迎面一锤，留给特朗普的时间不多了！ 外交部狠怼美国一把又亮了！特朗普最新意图在推特曝光！ 特朗普突然发推谈贸易战，释放强烈信号！ 刚刚，美国软肋彻底暴露！这次中国要狠狠打，把特朗普打回谈判桌！ 特朗普再甩千亿，欧日也想捅刀中国？不惜一切代价，打退来犯之敌！ 惹毛雄起中国！特朗普，你这次真要“掉得大”！ 狠狠打！打出30年经济和平！中国直击美国要害！ 贸易战逼中俄联手！中东终于捂不住了，一夜大乱！ 中美贸易战，最倒霉的竟是他们，你绝对想不到！ 强力实锤反击，中国对美加关税！中国打赢这场贸易战，能得到什么，会损失什么？ 中美贸易战，原来有大秘密，中国为何必须打赢？ 中国放的这句狠话，美国再听不懂就要出大事了！ 中国反击又一重锤！人民币代替美元结算，真是拳拳到肉！ 神秘而重磅的访问，他向世界释放7大信号！（文末大福利） 辽宁舰与至少40艘舰南海军演，中国展示势将贸易战打到底的决心！ 中国这记重锤，让美国内伤！上海原油期货上市，意义重磅！ 中美贸易战，美国必败的3大原因在此！ 中国已反击美国！贸易战，中国５招制敌！ 中美贸易战背后，是特朗普中东大阴谋！ 特朗普炮轰中国，贸易战找错对象，白宫犯三大战略性错误！ ";
        System.out.println(TextRankSummary.getTopSentenceList(document, 3));
    }

    /**
     * 将文章分割为句子
     * @param document
     * @return
     */
    static List<String> spiltSentence(String document)
    {
        List<String> sentences = new ArrayList<String>();
        if (document == null) return sentences;
        for (String line : document.split("[\r\n]"))
        {
            line = line.trim();
            if (line.length() == 0) continue;
            for (String sent : line.split("[，,。:：“”？?！!；;]"))
            {
                sent = sent.trim();
                if (sent.length() == 0) continue;
                sentences.add(sent);
            }
        }

        return sentences;
    }

    /**
     * 是否应当将这个term纳入计算，词性属于名词、动词、副词、形容词
     * @param term
     * @return 是否应当
     */
    public static boolean shouldInclude(Term term)
    {
        return CoreStopWordDictionary.shouldInclude(term);
    }

    /**
     * 一句话调用接口
     * @param document 目标文档
     * @param size 需要的关键句的个数
     * @return 关键句列表
     */
    public static List<String> getTopSentenceList(String document, int size)
    {
        List<String> sentenceList = spiltSentence(document);
        List<List<String>> docs = new ArrayList<List<String>>();
        for (String sentence : sentenceList)
        {
            List<Term> termList = HanLP.segment(sentence);
            List<String> wordList = new LinkedList<String>();
            for (Term term : termList)
            {
                if (shouldInclude(term))
                {
                    wordList.add(term.word);
                }
            }
            docs.add(wordList);
        }
        TextRankSummary textRankSummary = new TextRankSummary(docs);
        int[] topSentence = textRankSummary.getTopSentence(size);
        List<String> resultList = new LinkedList<String>();
        for (int i : topSentence)
        {
            resultList.add(sentenceList.get(i));
        }
        return resultList;
    }
}
