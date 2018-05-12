package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.Type;
import com.zhoulin.demo.mapper.TypeMapper;
import com.zhoulin.demo.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 17:43 2018/3/20
 */
@Component
public class TypeServiceImpl implements TypeService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeServiceImpl.class);
    
    @Autowired
    private TypeMapper typeMapper;
    @Override
    public Type getTypeById(Integer typeId) throws Exception {
        Type type = new Type();

        try {
            type = typeMapper.getTypeByTypeId(typeId);
            if(type!=null){
                return type;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Type> getAllType() throws Exception {

        try {
            List<Type> typeList = typeMapper.findAll();
            return typeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
