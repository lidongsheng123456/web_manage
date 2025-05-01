package com.example.system.service;

import com.example.system.domain.ComQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminComQueryService {
    /**
     * 新增通用查询
     *
     * @param comQuery
     */
    void addComQuery(ComQuery comQuery);

    /**
     * 批量删除通用查询
     *
     * @param ids
     */
    void batchDeleteComQuery(List<Long> ids);

    /**
     * 修改通用查询
     *
     * @param comQuery
     */
    void updateComQuery(ComQuery comQuery);

    /**
     * 查询通用查询
     *
     * @param comQuery
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<ComQuery> queryComQuery(ComQuery comQuery, Integer currentPage, Integer pageSize);

    /**
     * 查询全部通用查询
     *
     * @return
     */
    List<ComQuery> queryAllComQuery();

    /**
     * 根据id查询通用查询
     *
     * @param id
     * @return
     */
    ComQuery queryComQueryById(Long id);
}
