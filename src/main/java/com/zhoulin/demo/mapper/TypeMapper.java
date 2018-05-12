package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {

    public List<Type> findAll() throws Exception;

    public Type getTypeByTypeId(Integer typeId) throws Exception;
}
