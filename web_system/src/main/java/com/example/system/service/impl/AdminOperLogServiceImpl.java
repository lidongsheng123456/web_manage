package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.OperLog;
import com.example.system.mapper.AdminOperLogMapper;
import com.example.system.service.AdminOperLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOperLogServiceImpl implements AdminOperLogService {
    private final AdminOperLogMapper adminOperLogMapper;

    public AdminOperLogServiceImpl(AdminOperLogMapper adminOperLogMapper) {
        this.adminOperLogMapper = adminOperLogMapper;
    }

    /**
     * 删除日志
     *
     * @param id
     */
    @Override
    public void deleteOperLog(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminOperLogMapper.deleteOperLog(id));
    }

    /**
     * 批量删除日志
     *
     * @param ids
     */
    @Override
    public void batchDeleteOperLog(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminOperLogMapper.batchDeleteOperLog(ids));
    }

    /**
     * 修改日志
     *
     * @param operlog
     */
    @Override
    public void updateOperLog(OperLog operlog) {
        validateField(operlog);
        isSuccess(adminOperLogMapper.updateOperLog(operlog));
    }

    /**
     * 查询日志
     *
     * @param operLog
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<OperLog> queryOperLog(OperLog operLog, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<OperLog> list = adminOperLogMapper.queryOperLog(operLog);
        return PageInfo.of(list);
    }

    /**
     * 查询全部日志
     *
     * @return
     */
    @Override
    public List<OperLog> queryAllOperLog() {
        return adminOperLogMapper.queryAllOperLog();
    }

    /**
     * 根据id查询日志
     *
     * @param id
     * @return
     */
    @Override
    public OperLog queryOperLogById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        return adminOperLogMapper.queryOperLogById(id);
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

    /**
     * 验证字段
     *
     * @param operLog
     */
    public void validateField(OperLog operLog) {
        if (ObjectUtil.isEmpty(operLog.getId())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
    }
}
