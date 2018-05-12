package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.SearchLog;

import java.util.List;

public interface SearchLogService {

    public List<String> getNowSearchCount() throws Exception;

    public Integer addSearchCount(SearchLog searchLog) throws Exception;

}
