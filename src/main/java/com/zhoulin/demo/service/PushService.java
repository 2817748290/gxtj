package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.Info;
import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.UserInfo;

import java.util.List;

public interface PushService {

    public List<Info> pushInformation(long id) throws Exception;

    public List<UserInfo> pushInfoForRecessiveGroup(Integer typeId) throws Exception;

    public List<Info> logAnalyzForPush(Integer userId) throws Exception;

    public List<Info> pushInfoByTypeId(Integer typeId) throws Exception;

    public List<Info> pushInfoByUserGroup(Integer userId) throws Exception;

}
