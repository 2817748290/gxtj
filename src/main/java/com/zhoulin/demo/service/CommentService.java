package com.zhoulin.demo.service;

import com.zhoulin.demo.bean.InfoComment;

import java.util.List;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 19:53 2018/3/7
 */
public interface CommentService {
    /**
     * 获取资讯评论列表
     * @return
     * @throws Exception
     */
    public List<InfoComment> getAll() throws Exception;

    /**
     * 获取资讯评论列表
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    public List<InfoComment> getList(int page, int limit) throws Exception;

    /**
     * 获取最多赞文章
     *
     */
    public List<InfoComment> getMostLikesComments() throws Exception;

    /**
     * 根据资讯id获取评论列表
     * @param infoId
     * @return
     * @throws Exception
     */
    public List<InfoComment> getListByInfoId(Integer infoId) throws Exception;

    /**
     * 根据用户id获取评论列表
     * @param userId
     * @return
     * @throws Exception
     */
    public List<InfoComment> getCommentsByUserId(Integer userId) throws Exception;

    /**
     * 根据id获取资讯评论
     * @param id
     * @return
     * @throws Exception
     */
    public InfoComment getCommentById(Integer id) throws Exception;
//
//    /**
//     * 根据TableMessage获取查询到的资讯评论
//     * @param tableMessage
//     * @return
//     * @throws Exception
//     */
//    public BaseTableMessage getSearchList(BaseTableMessage tableMessage) throws Exception;
    /**
     * 新增一个资讯评论
     * @param comment
     * @return
     * @throws Exception
     */
    public InfoComment add(InfoComment comment) throws Exception;
    /**
     * 修改一个资讯评论
     * @param comment
     * @return
     * @throws Exception
     */
    public InfoComment update(InfoComment comment) throws Exception;

    /**
     * 删除一个资讯评论
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(Integer id) throws Exception;
}
