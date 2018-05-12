package com.zhoulin.demo.service.impl;

import com.jcseg.extractor.impl.TextRankKeyphraseExtractor;
import com.jcseg.extractor.impl.TextRankKeywordsExtractor;
import com.jcseg.tokenizer.core.*;
import com.zhoulin.demo.service.JcsegService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author 周林
 */
@Component
public class JcsegServiceImpl implements JcsegService {

    /**
     * @param content
     * @return
     */
    @Override
    public List<String> getKeyphrase(String content){

        JcsegTaskConfig config = new JcsegTaskConfig(true);
        config.setClearStopwords(true);
        config.setAppendCJKSyn(false);
        config.setEnSecondSeg(false);
        config.setKeepUnregWords(false);
        ADictionary dic = DictionaryFactory.createSingletonDictionary(config);

        ISegment seg = null;
        try {
            seg = SegmentFactory
                    .createJcseg(JcsegTaskConfig.COMPLEX_MODE, new Object[]{config, dic});
            TextRankKeyphraseExtractor extractor = new TextRankKeyphraseExtractor(seg);
            extractor.setMaxIterateNum(10);
            extractor.setWindowSize(5);
            extractor.setKeywordsNum(15);
            extractor.setMaxWordsNum(4);
            List<String> phrases;
            phrases = extractor.getKeyphraseFromString(content);
            return phrases;
        } catch (JcsegException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<String> getKeywordsphrase(String content)  {

        JcsegTaskConfig config = new JcsegTaskConfig(true);
        config.setClearStopwords(true);
        config.setAppendCJKSyn(false);
        config.setKeepUnregWords(false);
        ADictionary dic = DictionaryFactory.createSingletonDictionary(config);

        ISegment seg = null;
        try {
            seg = SegmentFactory
                    .createJcseg(JcsegTaskConfig.COMPLEX_MODE, new Object[]{config, dic});
            TextRankKeywordsExtractor extractor = new TextRankKeywordsExtractor(seg);
            extractor.setMaxIterateNum(100);
            extractor.setWindowSize(1);
            extractor.setKeywordsNum(8);

            List<String> phrases;
            phrases = extractor.getKeywordsFromString(content);
            return phrases;
        } catch (JcsegException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
