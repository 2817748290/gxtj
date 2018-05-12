package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.RecessiveGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecessiveGroupMapper {

    public List<RecessiveGroup> getUserRecessiveGroup(Integer typeId) throws Exception;

}
