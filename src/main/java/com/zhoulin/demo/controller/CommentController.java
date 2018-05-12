package com.zhoulin.demo.controller;

import com.zhoulin.demo.bean.InfoComment;
import com.zhoulin.demo.bean.Message;
import com.zhoulin.demo.bean.UserInfo;
import com.zhoulin.demo.service.CommentService;
import com.zhoulin.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public")
public class CommentController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CommentService commentService;
    /**
     * 查询所有点赞记录
     * @return
     */
    @RequestMapping(value = "/comment/all", method = RequestMethod.GET)
    @ResponseBody
    public Message userLikeList(){
        List<InfoComment> infoCommentList = new ArrayList<>();
        try {
            infoCommentList = commentService.getAll();
            return new Message(Message.SUCCESS,"获取评论成功！", infoCommentList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取评论失败！",null);
        }
    }
    /**
     * 查询一篇资讯的所有评论
     * @param id
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/information/{id}/comment", method = RequestMethod.GET)
    @ResponseBody
    public Message commentList(@PathVariable(value = "id") Integer id){
        List<InfoComment> commentList = new ArrayList<>();
        try {
            commentList = commentService.getListByInfoId(id);
            return new Message(Message.SUCCESS,"获取资讯评论成功！",commentList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯评论失败！",null);
        }
    }
    /**
     * 查询最热评论
     * @return
     */
    @RequestMapping(value = "/information/hotComments", method = RequestMethod.GET)
    @ResponseBody
    public Message getHotComments(){
        List<InfoComment> commentList = new ArrayList<>();
        try {
            commentList = commentService.getMostLikesComments();
            return new Message(Message.SUCCESS,"获取资讯评论成功！",commentList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取资讯评论失败！",null);
        }
    }

    /**
     * 新增一条评论
     * @param
     * @param comment
     * @return
     */
    @RequestMapping(value = "/information/{infoId}/comment", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@PathVariable(value = "infoId") Integer infoId, @RequestBody InfoComment comment){
        try {
            comment.setInfoId(infoId);
            InfoComment result = commentService.add(comment);
            return new Message(Message.SUCCESS,"增加资讯评论成功！",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"增加资讯评论失败！",null);
        }
    }
    /**
     * 修改一条评论
     * @param
     * @param comment
     * @return
     */
    @RequestMapping(value = "/information/comment", method = RequestMethod.PUT)
    @ResponseBody
    public Message update(@RequestBody InfoComment comment){
        try {
            InfoComment result = commentService.update(comment);
            return new Message(Message.SUCCESS,"修改资讯评论成功！",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改资讯评论失败！",null);
        }
    }
    /**
     * 查询用户评论
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/information/user/{userId}/comment", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@PathVariable(value = "userId") Integer userId){
        try {
            List<InfoComment> comments = commentService.getCommentsByUserId(userId);
            return new Message(Message.SUCCESS,"增加资讯评论成功！",comments);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"增加资讯评论失败！",null);
        }
    }
}
