package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.InfoType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoTypeMapper {

    public InfoType getTypeByTypeId(Integer typeId) throws Exception;

    public List<InfoType> findAllType() throws Exception;

}
