package com.zhoulin.demo.service.search;

import com.zhoulin.demo.bean.Information;
import com.zhoulin.demo.bean.form.InfoSearch;
import com.zhoulin.demo.bean.form.ServiceMultiResult;

/**
 * 检索接口
 */
public interface SearchService {

    /**
     * 索引目标资讯
     * @param id
     */
//    boolean index(long id);
    void index(long id);

    /**
     * 移除资讯索引
     * @param id
     */
    void remove(long id);

    /**
     * 查询资讯接口
     * @param infoSearch
     * @return
     */
    ServiceMultiResult<Long> query(InfoSearch infoSearch);

    boolean indexPro(long id);

    /**
     * 多条件匹配
     * @param infoSearch
     * @return
     */
    public ServiceMultiResult<Information> queryMultiMatch(InfoSearch infoSearch);

}
