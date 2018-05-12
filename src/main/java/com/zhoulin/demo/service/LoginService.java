package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserInfo;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    public Message login(UserInfo userInfo, HttpServletResponse response);

    public Message loginOut(HttpServletResponse response);

}
