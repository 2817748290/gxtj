package com.zhoulin.demo.controller;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 17:49 2018/3/20
 */

import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.Type;
import com.zhoulin.demo.service.TypeService;
import com.zhoulin.demo.utils.CheckType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TypeInfoController 控制类
 */
@RestController
@RequestMapping("/public/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private CheckType checkType;

    /**
     * 获取所有type记录
     * @return
     */
    @RequestMapping(value = "/getAllType", method = RequestMethod.GET)
    @ResponseBody
    public Message getAllType(){
        List<Type> typeList = new ArrayList<>();
        try {
            typeList = typeService.getAllType();
            if (typeList != null){
                return new Message(Message.SUCCESS,"获取类别列表--成功",typeList);
            } else {
                return new Message(Message.FAILURE,"获取类别列表--失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取类别列表异常！",null);
        }
    }

    /**
     * 根据【类别编号】获取对应类别信息
     * @param typeId 类别编号
     * @return
     */
    @RequestMapping(value = "/getTypeById/{typeId}", method = RequestMethod.GET)
    @ResponseBody
    public Message getTypeInfoById(@PathVariable(value = "typeId") Integer typeId){
        Type type = new Type();
        try {
            type = typeService.getTypeById(typeId);


            if (type != null){
                return new Message(Message.SUCCESS,"获取类别信息--成功",type);
            } else {
                return new Message(Message.FAILURE,"获取类别信息--失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取类别信息异常！",null);
        }
    }

}
