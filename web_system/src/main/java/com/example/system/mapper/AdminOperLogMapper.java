package com.example.system.mapper;

import com.example.system.domain.OperLog;

import java.util.List;

public interface AdminOperLogMapper {
    /**
     * 新增日志
     *
     * @param operlog
     * @return
     */
    int addOperLog(OperLog operlog);

    /**
     * 删除日志
     *
     * @param id
     * @return
     */
    int deleteOperLog(Long id);

    /**
     * 批量删除日志
     *
     * @param ids
     * @return
     */
    int batchDeleteOperLog(List<Long> ids);

    /**
     * 修改日志
     *
     * @param operlog
     * @return
     */
    int updateOperLog(OperLog operlog);

    /**
     * 查询日志
     *
     * @param operLog
     * @return
     */
    List<OperLog> queryOperLog(OperLog operLog);

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
