package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.SearchLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchLogMapper {

    public List<SearchLog> getNowSearchCount() throws Exception;

    public Integer addSearchCount(SearchLog searchLog) throws Exception;

}
