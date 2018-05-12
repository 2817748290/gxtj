package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.TypeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface TypeRelationMapper {

    public TypeRelation getInfoByTRId(long id) throws Exception;

    public List<TypeRelation> getInfoByTypeId(Integer typeId) throws Exception;

    public Integer addTypeRelation(TypeRelation typeRelation) throws Exception;

    public Integer updateTypeRelationByInfoId(TypeRelation typeRelation) throws Exception;

    public int getCountByTypeId(Integer typeId) throws Exception;

}
