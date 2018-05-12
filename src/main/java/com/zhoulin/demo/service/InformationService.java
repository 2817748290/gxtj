package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.form.InfoSearch;

import java.util.List;

public interface InformationService {

    public InfoSearch getInitInfo(InfoSearch infoSearch) throws Exception;

    public Information getInfoByInfoId(long infoId) throws Exception;

    public Integer updateInformation(Information information) throws Exception;

    public Integer deleteInformationById(long infoId) throws Exception;

    public List<Information> findAll() throws Exception;

    public List<Information> findInfoByDate() throws Exception;

}
