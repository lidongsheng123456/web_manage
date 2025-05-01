package com.example.system.service;

import com.example.system.domain.Dict;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminDictService {
    /**
     * 新增字典
     *
     * @param dict
     */
    void addDict(Dict dict);

    /**
     * 批量删除字典
     *
     * @param ids
     */
    void batchDeleteDict(List<Long> ids);

    /**
     * 修改字典
     *
     * @param dict
     */
    void updateDict(Dict dict);

    /**
     * 查询字典
     *
     * @param dict
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<Dict> queryDict(Dict dict, Integer currentPage, Integer pageSize);

    /**
     * 查询全部字典
     *
     * @return
     */
    List<Dict> queryAllDict();

    /**
     * 根据id查询字典
     *
     * @param id
     * @return
     */
    Dict queryDictById(Long id);
}
