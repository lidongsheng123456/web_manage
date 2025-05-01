package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Dict;
import com.example.system.mapper.AdminDictMapper;
import com.example.system.service.AdminDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDictServiceImpl implements AdminDictService {

    private final AdminDictMapper adminDictMapper;


    /**
     * 新增字典
     *
     * @param dict
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    public void addDict(Dict dict) {
        isSuccess(adminDictMapper.addDict(dict));
    }

    /**
     * 批量删除字典
     *
     * @param ids
     */
    @Override
    public void batchDeleteDict(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminDictMapper.batchDeleteDict(ids));
    }

    /**
     * 修改字典
     *
     * @param dict
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateDict(Dict dict) {
        isSuccess(adminDictMapper.updateDict(dict));
    }

    /**
     * 查询字典
     *
     * @param dict
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Dict> queryDict(Dict dict, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Dict> list = adminDictMapper.queryDict(dict);
        return PageInfo.of(list);
    }

    /**
     * 查询全部字典
     *
     * @return
     */
    @Override
    public List<Dict> queryAllDict() {
        return adminDictMapper.queryAllDict();
    }

    /**
     * 根据id查询字典
     *
     * @param id
     * @return
     */
    @Override
    public Dict queryDictById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        return adminDictMapper.queryDictById(id);
    }

    /**
     * 是否成功
     *
     * @param i
     */
    public void isSuccess(Integer i) {
        if (i == 0) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
