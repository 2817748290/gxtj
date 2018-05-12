package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.UserRead;
import com.zhoulin.demo.mapper.UserReadMapper;
import com.zhoulin.demo.service.UserReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserReadServiceImpl implements UserReadService {

    @Autowired
    private UserReadMapper userReadMapper;

    @Override
    public List<UserRead> getUserReadByUserId(Integer userId) {

        List<UserRead> userReadList = new ArrayList<>();
        try {
            userReadList = userReadMapper.getUserReadByUserId(userId);
            return userReadList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer addUserRead(UserRead userRead) {

        Integer addStatus = 0;

        try {
            addStatus = userReadMapper.addUserRead(userRead);

            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }


    }

    @Override
    public Integer updateUserRead(UserRead userRead) throws Exception {
        Integer addStatus = 0;

        try {
            addStatus = userReadMapper.addUserRead(userRead);

            return addStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public UserRead findUserReadExist(int userId, long infoId){

        UserRead userRead = new UserRead();

        try {
            userRead = userReadMapper.findUserReadExist(userId, infoId);
            return userRead;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
