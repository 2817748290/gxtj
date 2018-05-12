package com.textrank;


import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;

import java.util.*;

/**
 * TextRank关键词提取
 * @author hankcs
 */
public class TextRankKeyword
{
    public static final int nKeyword = 5;
    /**
     * 阻尼系数（ＤａｍｐｉｎｇＦａｃｔｏｒ），一般取值为0.85
     */
    static final float d = 0.85f;
    /**
     * 最大迭代次数
     */
    static final int max_iter = 200;
    static final float min_diff = 0.001f;

    public TextRankKeyword()
    {
        // jdk bug : Exception in thread "main" java.lang.IllegalArgumentException: Comparison method violates its general contract!
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

    public List<String> getKeyword(String title, String content)
    {
        List<Term> termList = HanLP.segment(title + content);
//        System.out.println(termList);
        List<String> wordList = new ArrayList<String>();
        for (Term t : termList)
        {
            if (shouldInclude(t))
            {
                wordList.add(t.word);
            }
        }
//        System.out.println(wordList);
        Map<String, Set<String>> words = new HashMap<String, Set<String>>();
        Queue<String> que = new LinkedList<String>();
        for (String w : wordList)
        {
            if (!words.containsKey(w))
            {
                words.put(w, new HashSet<String>());
            }
            que.offer(w);
            if (que.size() > 5)
            {
                que.poll();
            }

            for (String w1 : que)
            {
                for (String w2 : que)
                {
                    if (w1.equals(w2))
                    {
                        continue;
                    }

                    words.get(w1).add(w2);
                    words.get(w2).add(w1);
                }
            }
        }
//        System.out.println(words);
        Map<String, Float> score = new HashMap<String, Float>();
        for (int i = 0; i < max_iter; ++i)
        {
            Map<String, Float> m = new HashMap<String, Float>();
            float max_diff = 0;
            for (Map.Entry<String, Set<String>> entry : words.entrySet())
            {
                String key = entry.getKey();
                Set<String> value = entry.getValue();
                m.put(key, 1 - d);
                for (String other : value)
                {
                    int size = words.get(other).size();
                    if (key.equals(other) || size == 0) continue;
                    m.put(key, m.get(key) + d / size * (score.get(other) == null ? 0 : score.get(other)));
                }
                max_diff = Math.max(max_diff, Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
            }
            score = m;
            if (max_diff <= min_diff) break;
        }
        List<Map.Entry<String, Float>> entryList = new ArrayList<Map.Entry<String, Float>>(score.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Float>>()
        {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2)
            {
                return (o1.getValue() - o2.getValue() > 0 ? -1 : 1);
            }
        });
//        System.out.println(entryList);
        String result = "";
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < nKeyword; ++i)
        {
//            result += entryList.get(i).getKey() + '#';
            resultList.add(entryList.get(i).getKey());
        }
        return resultList;
    }

    public static void main(String[] args)
    {
        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";

        String kw = "传来, XX, 左兆燕, 申汗勤, 秦汝秀, 借款, 法院, 事实, 证据";

        String doc = "钛媒体注：据新浪手机报道，大数据服务商QuestMobile近日公布了2018年一季度中国移动互联网数据报告，报告中显示了今年第一季度各大手机厂商在国内的表现。除了苹果和三星，中国智能终端份额排行榜上前十已经没有国外品牌。国产手机总体份额目前已占到了国内手机市场的56.6%，成为中坚力量，对抗着苹果的一家独大。   统计时间是截至2018年3月底，很多在3月上市开卖的新机没统计在内 根据数据来看，截至2018年3月，中国手机市场用户规模最大的品牌依然是苹果，份额高达25.7%，但有小幅下滑。而OPPO则成功反超华为爬上第二，成为国产手机第一，份额为17.2%。华为位居第三，份额为16.8%，vivo位居第四，份额为12.3%。小米排在第五，份额为10.3%。 排在后面的厂商分别为三星，金立，魅族，酷派，中兴和联想，而在2017年年末的调研中依然有0.4%份额的HTC，则已经划归到“Others”里面去了，现状凄凉。 相信随着OPPO R15、vivo X21、华为P20 Pro和小米等热门机型的开卖，OPPO、vivo和华为三家品牌的份额还能有一定的上升  更多精彩内容，关注钛媒体微信号（ID：taimeiti），或者下载钛媒体App    ";

        List<String> keywords = new TextRankKeyword().getKeyword("", doc);

//        for (String keyword:keywords) {
//            System.out.println("k:  " + keyword);
//        }
        System.out.println("k:  " + keywords);

    }

    /**
     * 是否应当将这个term纳入计算，词性属于名词、动词、副词、形容词
     * @param term
     * @return 是否应当
     */
    public boolean shouldInclude(Term term)
    {
        return CoreStopWordDictionary.shouldInclude(term);
    }
}
