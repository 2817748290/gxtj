package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.InfoContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoContentMapper {

    public InfoContent getInfoContentByInfoId(long infoId) throws Exception;

    public Integer addInfoContent(InfoContent infoContent) throws Exception;

}
