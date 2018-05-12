package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.Info;

import java.util.List;

public interface InfoService {

    public Info getInfoByInfoId(long infoId) throws Exception;

    public Info getInfoByInfoIdForImage(long infoId) throws Exception;

    public Integer updateInfo(Info info) throws Exception;

    public Integer deleteInfoById(long infoId) throws Exception;

    public List<Info> findAll() throws Exception;

    public List<Info> findInfoByDate(int limitNum) throws Exception;

    public List<String> getHotWords() throws Exception;

    public int allCount() throws Exception;

}
