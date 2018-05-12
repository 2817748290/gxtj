package com.zhoulin.demo.service.impl;

import com.zhoulin.demo.bean.BaseTableMessage;
import com.zhoulin.demo.bean.InfoComment;
import com.zhoulin.demo.mapper.CommentMapper;
import com.zhoulin.demo.mapper.UserInfoMapper;
import com.zhoulin.demo.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 19:54 2018/3/7
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserInfoMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
//    @Override
//    public List<InfoComment> getAll() throws Exception {
//        List<Info> articleList = new ArrayList<>();
//        List<InfoTag> tagList = new ArrayList<>();
//        InfoClass articleClass = new InfoClass();
//        try {
//
//            articleList = articleMapper.getAll();
//            for (Info article: articleList){
//
//                Integer id = article.getId();
//                Integer clazz = article.getClazz();
//                //todo
//                //通过文章id获取她的标签列表
//                article.setTagList(tagMapper.getListByInfoId(id));
//                article.setInfoClass(classMapper.getById(clazz));
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return articleList;
//    }


    @Override
    public List<InfoComment> getAll() throws Exception {
        List<InfoComment> infoCommentList = new ArrayList<>();

        try {
            infoCommentList = commentMapper.getAll();

            return infoCommentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<InfoComment> getList(int page, int limit) {
//        List<InfoComment> commentList = new ArrayList<>();
//        BaseTableMessage tableMessage = new BaseTableMessage();
//        try {
//            tableMessage.setLimit(limit);
//            tableMessage.setOffset((page-1)*limit);
//            commentList = commentMapper.getList(tableMessage);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return commentList;
        return null;
    }

    @Override
    public List<InfoComment> getMostLikesComments() {
        List<InfoComment> commentList = new ArrayList<>();
        try {
            commentList = commentMapper.getMostLikesComments();
            for (InfoComment comment: commentList){
                Integer userId = comment.getUserId();
                if (userId!=null){
                    comment.setUserInfo(userMapper.getUserInfoById(userId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return commentList;
    }

    @Override
    public List<InfoComment> getListByInfoId(Integer id) {
        List<InfoComment> commentList = new ArrayList<>();
        BaseTableMessage tableMessage = new BaseTableMessage();
        try {
//            tableMessage.setLimit(limit);
//            tableMessage.setOffset((page-1)*limit);
            commentList = commentMapper.getListByInfoId(id);
            for (InfoComment comment: commentList){
                Integer userId = comment.getUserId(), replyUserId = comment.getReplyUserId();
                if (userId!=null){
                    comment.setUserInfo(userMapper.getUserInfoById(userId));
                }
                if (replyUserId!=null) {
                    comment.setReplyUser(userMapper.getUserInfoById(comment.getReplyUserId()));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return commentList;
    }

    @Override
    public List<InfoComment> getCommentsByUserId(Integer userId) throws Exception {
        List<InfoComment> commentList = new ArrayList<>();
        BaseTableMessage tableMessage = new BaseTableMessage();
        try {
            commentList = commentMapper.getCommentsByUserId(userId);
            for (InfoComment comment: commentList){
                Integer replyUserId = comment.getReplyUserId();
                if (replyUserId!=null) {
                    comment.setReplyUser(userMapper.getUserInfoById(comment.getReplyUserId()));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return commentList;

    }

    @Override
    public InfoComment add(InfoComment comment) {
        InfoComment result = null;
        try {
            commentMapper.insert(comment);
            result = this.getCommentById(comment.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean delete(Integer id) {
        boolean state = false;
        try {
            state = commentMapper.delete(id) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }
    //
    @Override
    public InfoComment getCommentById(Integer id) {
        InfoComment comment = new InfoComment();
        try {
            comment = commentMapper.getCommentById(id);
            int userId = comment.getUserId();
            Integer replyUserId = comment.getReplyUserId();
            if(replyUserId != null){
                comment.setReplyUser(userMapper.getUserInfoById(replyUserId));
            }
            comment.setUserInfo(userMapper.getUserInfoById(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return comment;
    }
//
//    @Override
//    public BaseTableMessage getSearchList(BaseTableMessage tableMessage) throws Exception {
//        return null;
//    }
//

    //
    @Override
    public InfoComment update(InfoComment comment) {
        InfoComment result = null;
        try {
            commentMapper.update(comment);
            result = this.getCommentById(comment.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
//
//
//    @Override
//    public Info add(Info article) {
//        Info result = null;
//        try {
//            articleMapper.insert(article);
//            result = articleMapper.getById(article.getId());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @Override
//    public Info update(Info article) {
//        Info result = null;
//        try {
//            int row = articleMapper.update(article);
//            if(row >= 1){
//                result = articleMapper.getById(article.getId());
//            }else{
//                return null;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }
//
}
