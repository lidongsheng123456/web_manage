package com.example.system.mapper;

import com.example.system.domain.Dict;

import java.util.List;

public interface AdminDictMapper {
    /**
     * 新增字典
     *
     * @param dict
     * @return
     */
    int addDict(Dict dict);

    /**
     * 批量删除字典
     *
     * @param ids
     * @return
     */
    int batchDeleteDict(List<Long> ids);

    /**
     * 修改字典
     *
     * @param dict
     * @return
     */
    int updateDict(Dict dict);

    /**
     * 查询字典
     *
     * @param dict
     * @return
     */
    List<Dict> queryDict(Dict dict);

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
