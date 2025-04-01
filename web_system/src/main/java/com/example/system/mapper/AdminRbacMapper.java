package com.example.system.mapper;

import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

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
}
