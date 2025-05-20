package com.example.system.service;

import com.example.system.domain.OperLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminOperLogService {
    /**
     * 批量删除日志
     *
     * @param ids
     */
    void batchDeleteOperLog(List<Long> ids);

    /**
     * 修改日志
     *
     * @param operlog
     */
    void updateOperLog(OperLog operlog);

    /**
     * 查询日志
     *
     * @param operLog
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<OperLog> queryOperLog(OperLog operLog, Integer currentPage, Integer pageSize);

    /**
     * 查询全部日志
     *
     * @return
     */
    List<OperLog> queryAllOperLog();

    /**
     * 根据id查询日志
     *
     * @param id
     * @return
     */
    OperLog queryOperLogById(Long id);
}
