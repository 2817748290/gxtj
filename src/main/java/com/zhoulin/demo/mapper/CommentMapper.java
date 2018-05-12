package com.zhoulin.demo.mapper;

import com.zhoulin.demo.bean.BaseTableMessage;
import com.zhoulin.demo.bean.InfoComment;
import com.zhoulin.demo.bean.Information;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 查询所有资讯评论列表
     * @return资讯评论列表
     * @throws Exception
     */
    public List<InfoComment> getAll() throws Exception;

    /**
     * 根据主键获取资讯评论
     * @param id
     * @return
     * @throws Exception
     */
    public InfoComment getCommentById(Integer id) throws Exception;

    /**
     * 根据TableMessage和资讯评论组别查询资讯评论
     * @param tableMessage
     * @return
     */
    //public List<InfoComment> getList(BaseTableMessage tableMessage) throws Exception;
    /**
     * 根据资讯id查询资讯评论
     * @param
     * @return
     */
    public List<InfoComment> getListByInfoId(@Param("infoId") Integer id) throws Exception;

    /**
     * 根据TableMessage获取查询到的资讯评论数量
     * @param tableMessage
     * @return
     * @throws Exception
     */
    // public Integer searchArticleCount(ArticleTableMessage tableMessage) throws Exception;

    /**
     * 新增一个资讯评论
     * @param comment
     * @return
     */
    public int insert(InfoComment comment) throws Exception;

    /**
     * 修改一个资讯评论
     */
    public int update(InfoComment comment) throws Exception;

    /**
     * 删除一个资讯评论
     */
    public int delete(Integer id) throws Exception;

    /**
     * 获取最多赞文章
     */
    public List<InfoComment> getMostLikesComments() throws Exception;

    /**
     * 获取某个用户文章
     */
    public List<InfoComment> getCommentsByUserId(Integer userId) throws Exception;

}
