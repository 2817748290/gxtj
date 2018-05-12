package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.Information;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InformationMapper {

    public Information getInfoByInfoId(long infoId) throws Exception;

    public Integer updateInformation(Information information) throws Exception;

    public Integer deleteInformationById(long infoId) throws Exception;

    public List<Information> findAll() throws Exception;

    public List<Information> findInfoByDate() throws Exception;

    public Integer addInformation(Information information) throws Exception;

}
