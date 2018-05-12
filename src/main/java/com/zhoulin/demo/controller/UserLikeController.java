package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.InfoComment;
import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserLike;
import com.zhoulin.demo.service.CommentService;
import com.zhoulin.demo.service.UserInfoService;
import com.zhoulin.demo.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public")
public class UserLikeController {

    @Autowired
    private UserLikeService userLikeService;
    /**
     * 查询所有点赞记录
     * @return
     */
    @RequestMapping(value = "/userLike/all", method = RequestMethod.GET)
    @ResponseBody
    public Message userLikeList(){
        List<UserLike> userLikeList = new ArrayList<>();
        try {
            userLikeList = userLikeService.findAll();
            return new Message(Message.SUCCESS,"获取点赞记录成功！",userLikeList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取点赞记录失败！",null);
        }
    }
    /**
     * 删除一条点赞记录
     * @param
     * @param userLikeId
     * @return
     */
    @RequestMapping(value = "/userLike/{userLikeId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Message del(@PathVariable("userLikeId") Integer userLikeId){
        try {
            boolean result = userLikeService.deleteUserLikeById(userLikeId);
            if(result){
                return new Message(Message.SUCCESS,"删除点赞记录成功！",result);
            }else {
                return new Message(Message.SUCCESS,"删除点赞记录失败！",result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除点赞记录失败！",null);
        }
    }
    /**
     * 新增一条点赞记录
     * @param
     * @param userLike
     * @return
     */
    @RequestMapping(value = "/userLike", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@RequestBody UserLike userLike){
        try {
            UserLike result = userLikeService.addUserLike(userLike);
            return new Message(Message.SUCCESS,"增加点赞记录成功！",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"增加点赞记录失败！",null);
        }
    }
    /**
     * 修改一条点赞记录
     * @param
     * @param userLike
     * @return
     */
    @RequestMapping(value = "/userLike", method = RequestMethod.PUT)
    @ResponseBody
    public Message update(@RequestBody UserLike userLike){
        try {
            UserLike result = userLikeService.updateUserLike(userLike);
            return new Message(Message.SUCCESS,"修改点赞记录成功！",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改点赞记录失败！",null);
        }
    }

}
