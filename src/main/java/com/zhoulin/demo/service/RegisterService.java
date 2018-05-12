package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.UserInfo;

public interface RegisterService {

    /**
     * 用户邮箱注册
     * @throws Exception
     */
    public void registerUserInfo(UserInfo userInfo, String token) throws Exception;


    /**
     * 用户邮箱激活
     * @param code 激活码
     * @throws Exception
     */
    public UserInfo activationUserInfo(String code) throws Exception;

    /**
     * 密码找回
     * @param emailUrl
     * @param token
     * @throws Exception
     */
    public void findUserPassword(String emailUrl, String token) throws Exception;

}
