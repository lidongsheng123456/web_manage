package com.example.system.mapper;

import com.example.common.annotation.TenantIgnore;
import com.example.system.domain.vo.DataCountTypeVo;

import java.util.List;

@TenantIgnore
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

    /**
     * 统计前台用户总数
     *
     * @return
     */
    List<DataCountTypeVo> frontUserCount();

    /**
     * 统计角色总数
     *
     * @return
     */
    List<DataCountTypeVo> roleCount();

    /**
     * 统计字典数据总数
     *
     * @return
     */
    List<DataCountTypeVo> dictDataCount();

    /**
     * 统计通用查询总数
     *
     * @return
     */
    List<DataCountTypeVo> comQueryCount();

    /**
     * 统计角色权限关联总数
     *
     * @return
     */
    List<DataCountTypeVo> rolePermissionCount();

    /**
     * 统计用户角色关联总数
     *
     * @return
     */
    List<DataCountTypeVo> userRoleCount();

    /**
     * 查询各表总记录数
     *
     * @return
     */
    Long totalUserCount();

    Long totalNoticeCount();

    Long totalOperLogCount();

    Long totalPermissionCount();

    Long totalFrontUserCount();

    Long totalRoleCount();

    Long totalDictDataCount();

    Long totalComQueryCount();

    Long totalRolePermissionCount();

    Long totalUserRoleCount();
}
