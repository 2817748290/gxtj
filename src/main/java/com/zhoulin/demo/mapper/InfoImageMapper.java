package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.InfoImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoImageMapper {

    public InfoImage getInfoImageByInfoId(long id) throws Exception;

    public Integer addInfoImage(InfoImage infoImage) throws Exception;

}
