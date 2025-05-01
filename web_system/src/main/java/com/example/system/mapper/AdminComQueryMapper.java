package com.example.system.mapper;

import com.example.system.domain.ComQuery;

import java.util.List;

public interface AdminComQueryMapper {
    /**
     * 新增通用查询
     *
     * @param comQuery
     * @return
     */
    int addComQuery(ComQuery comQuery);

    /**
     * 批量删除通用查询
     *
     * @param ids
     * @return
     */
    int batchDeleteComQuery(List<Long> ids);

    /**
     * 修改通用查询
     *
     * @param comQuery
     * @return
     */
    int updateComQuery(ComQuery comQuery);

    /**
     * 查询通用查询
     *
     * @param comQuery
     * @return
     */
    List<ComQuery> queryComQuery(ComQuery comQuery);

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
