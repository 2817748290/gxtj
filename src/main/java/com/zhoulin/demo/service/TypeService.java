package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.Type;

import java.util.List;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 17:41 2018/3/20
 */
public interface TypeService {

    public Type getTypeById(Integer typeId) throws Exception;

    public List<Type> getAllType() throws Exception;

}
