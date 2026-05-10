package com.example.system.mapper;

import com.example.common.annotation.TenantIgnore;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@TenantIgnore
public interface AdminRbacMapper {

    /**
     * 根据登录id查询权限
     *
     * @param loginId
     * @return
     */
    @MapKey("loginId")
    List<Map<String, Object>> getPermissionList(Long loginId);

    /**
     * 根据登录id查询角色
     *
     * @param loginId
     * @return
     */
    @MapKey("loginId")
    List<Map<String, Object>> getRoleList(Long loginId);

    /**
     * 批量查询用户权限（解决 N+1 问题）
     *
     * @param userIds
     * @return
     */
    List<Map<String, Object>> getBatchPermissionList(@Param("userIds") List<Long> userIds);

    /**
     * 批量查询用户角色（解决 N+1 问题）
     *
     * @param userIds
     * @return
     */
    List<Map<String, Object>> getBatchRoleList(@Param("userIds") List<Long> userIds);
}
