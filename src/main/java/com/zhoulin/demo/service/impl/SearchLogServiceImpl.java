package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.SearchLog;
import com.zhoulin.demo.mapper.SearchLogMapper;
import com.zhoulin.demo.service.JcsegService;
import com.zhoulin.demo.service.SearchLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchLogServiceImpl implements SearchLogService {

    private static final Logger logger = LoggerFactory.getLogger(SearchLogServiceImpl.class);

    @Autowired
    private SearchLogMapper searchLogMapper;

    @Autowired
    private JcsegService jcsegService;

    @Override
    public List<String> getNowSearchCount() {

        List<SearchLog> searchLogs = new ArrayList<>();
        String searchContent = "";
        try {
            searchLogs = searchLogMapper.getNowSearchCount();

            for (SearchLog searchLog : searchLogs){
                searchContent = searchContent + searchLog.getSearchContent() + " ";
            }
            List<String> phrase = jcsegService.getKeywordsphrase(searchContent);
            logger.info("获取的搜索热词  " + phrase.toString());
            return phrase;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer addSearchCount(SearchLog searchLog)  {
        Integer addStatus = 0;
        try {
            addStatus = searchLogMapper.addSearchCount(searchLog);
            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
