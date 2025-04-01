package com.example.system.mapper;

import com.example.system.domain.vo.DataCountTypeVo;

import java.util.List;

public interface AdminDataCenterMapper {
    /**
     * 统计用户总数
     *
     * @return
     */
    List<DataCountTypeVo> userCount();

    /**
     * 统计通知总数
     *
     * @return
     */
    List<DataCountTypeVo> noticeCount();

    /**
     * 统计操作日志总数
     *
     * @return
     */
    List<DataCountTypeVo> operLogCount();

    /**
     * 统计访问权限总数
     *
     * @return
     */
    List<DataCountTypeVo> permissionCount();
}
