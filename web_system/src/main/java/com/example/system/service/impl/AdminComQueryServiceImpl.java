package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.ComQuery;
import com.example.system.mapper.AdminComQueryMapper;
import com.example.system.service.AdminComQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminComQueryServiceImpl implements AdminComQueryService {

    private final AdminComQueryMapper adminDictMapper;


    /**
     * 新增通用查询
     *
     * @param comQuery
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    public void addComQuery(ComQuery comQuery) {
        isSuccess(adminDictMapper.addComQuery(comQuery));
    }

    /**
     * 批量删除通用查询
     *
     * @param ids
     */
    @Override
    public void batchDeleteComQuery(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminDictMapper.batchDeleteComQuery(ids));
    }

    /**
     * 修改通用查询
     *
     * @param comQuery
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateComQuery(ComQuery comQuery) {
        isSuccess(adminDictMapper.updateComQuery(comQuery));
    }

    /**
     * 查询通用查询
     *
     * @param comQuery
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<ComQuery> queryComQuery(ComQuery comQuery, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ComQuery> list = adminDictMapper.queryComQuery(comQuery);
        return PageInfo.of(list);
    }

    /**
     * 查询全部通用查询
     *
     * @return
     */
    @Override
    public List<ComQuery> queryAllComQuery() {
        return adminDictMapper.queryAllComQuery();
    }

    /**
     * 根据id查询通用查询
     *
     * @param id
     * @return
     */
    @Override
    public ComQuery queryComQueryById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        return adminDictMapper.queryComQueryById(id);
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
