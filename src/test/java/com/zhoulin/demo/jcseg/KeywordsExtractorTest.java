package com.zhoulin.demo.jcseg;

import com.jcseg.extractor.impl.TextRankKeywordsExtractor;
import com.jcseg.tokenizer.core.*;

import java.io.IOException;
import java.util.List;

public class KeywordsExtractorTest {

    public static void main(String[] args) 
    {
        //create your JcsegTaskConfig here please
        JcsegTaskConfig config = new JcsegTaskConfig(true);
        config.setClearStopwords(true);
        config.setAppendCJKSyn(false);
        config.setKeepUnregWords(false);
        ADictionary dic = DictionaryFactory.createSingletonDictionary(config);
        
        try {
            ISegment seg = SegmentFactory
                    .createJcseg(JcsegTaskConfig.DETECT_MODE, new Object[]{config, dic});
            
            TextRankKeywordsExtractor extractor = new TextRankKeywordsExtractor(seg);
            extractor.setMaxIterateNum(100);
            extractor.setWindowSize(1);
            extractor.setKeywordsNum(5);
            
            String doc = "";
            List<String> keywords;
            /*doc = "当中国南方还处于深秋时节，中国的北方已经下了几场雪了。"
                + "不知道大家在下雪的时候是选择在家上网休息，还是会到户外享受美妙的雪景呢？"
                + "但是小编作为南方人是真的很羡慕北方的孩纸们....  话说回来，"
                + "前两天北京下雪后小编的朋友圈就已经被刷屏了...尽管都是雪景，"
                + "但是大多数人还是从欣赏的角度出发。。。除了这些逗比：其实我们今天要说的事情是这样的，"
                + "最近网络中曝出一组中国人民公安大学学生在大雪天训练的照片...  "
                + "20岁出头的小鲜肉们在此严酷的环境中训练，小编还是替他们的女朋友感到心痛的...   "
                + "但是，作为将来要保家卫国的军人，公安大学的学生们在如此寒冷的天气中赤膊上阵训练更多的是让人振奋！"
                + "在曝光的训练队伍中甚至还有一名女生！  相对于其他男人赤膊上阵，这名女生也仅仅穿了一件T恤！"
                + "让人无比钦佩！  在雪中训练已经成为军人日常训练之一。在北方冬天动辄零下10几度的气温中训练，"
                + "无论对于人的耐力还是毅力都是一个极大的考验。  这样的雪浴训练更是官兵们的必修课：     当然，"
                + "其他常规训练也是必不可少的：     网友评论： @修坡贱：一入警校深似海，从此女票是路人 "
                + "@官庄工区派出所社区大队：抓紧锻炼身体，上班啃老本。女票不女票全都然并卵。 "
                + "@ilovelilybabe：好man好喜欢 @_陈毅勋：我想知道的是后来他们生病了没有。 "
                + "@-Davie-：好想去公安大学看看，不愧为中国第一公安院校，佩服！  相对于雪地训练历史更早的韩国和日本，"
                + "中国对于士兵的雪地训练开展得相对较晚，但是随着中国军人培养机制的日渐成熟，"
                + "雪地训练已经成为学兵和官兵们重要的训练科目！看到祖国的未来在大雪中艰苦的训练，你是怎样的心情呢？"
                + "赶紧分享出去让你的小伙伴们也看到吧！";*/
            //insert you test text here
            doc = "钛媒体注：据新浪手机报道，大数据服务商QuestMobile近日公布了2018年一季度中国移动互联网数据报告，报告中显示了今年第一季度各大手机厂商在国内的表现。除了苹果和三星，中国智能终端份额排行榜上前十已经没有国外品牌。国产手机总体份额目前已占到了国内手机市场的56.6%，成为中坚力量，对抗着苹果的一家独大。   统计时间是截至2018年3月底，很多在3月上市开卖的新机没统计在内 根据数据来看，截至2018年3月，中国手机市场用户规模最大的品牌依然是苹果，份额高达25.7%，但有小幅下滑。而OPPO则成功反超华为爬上第二，成为国产手机第一，份额为17.2%。华为位居第三，份额为16.8%，vivo位居第四，份额为12.3%。小米排在第五，份额为10.3%。 排在后面的厂商分别为三星，金立，魅族，酷派，中兴和联想，而在2017年年末的调研中依然有0.4%份额的HTC，则已经划归到“Others”里面去了，现状凄凉。 相信随着OPPO R15、vivo X21、华为P20 Pro和小米等热门机型的开卖，OPPO、vivo和华为三家品牌的份额还能有一定的上升  更多精彩内容，关注钛媒体微信号（ID：taimeiti），或者下载钛媒体App     " ;
        
//            doc = "保护作品完整权，作为著作权人一项重要的人身性权利，是为保障著作权人通过其作品忠实体现其人格和思想的权利。保护作品完整权规范的行为为“歪曲”、“篡改”，“歪曲”是指对作品作有违作者本意的体现，“篡改”是指以作伪手段进行改动，并以作者声誉是否受到损害和作者的社会评价是否降低为判断标准。然而实践中经常与改";
            keywords = extractor.getKeywordsFromString(doc);
            //keywords = extractor.getKeywordsFromFile("/home/chenxin/curpos/1.txt");
            System.out.println(keywords);
        } catch (JcsegException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
