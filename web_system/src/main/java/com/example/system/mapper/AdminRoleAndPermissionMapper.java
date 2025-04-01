package com.example.system.mapper;

import java.util.List;

public interface AdminRoleAndPermissionMapper {
    void deleteRoleAndPermissionByRoleId(Long id);

    /**
     * 根据角色id批量删除角色和权限关联表
     *
     * @param ids
     */
    void batchDeleteRoleAndPermissionByRoleId(List<Long> ids);

    /**
     * 根据权限id删除角色和权限关联表
     *
     * @param id
     */
    void deleteRoleAndPermissionByPermissionId(Long id);

    /**
     * 根据权限id批量删除角色和权限关联表
     *
     * @param ids
     */
    void batchDeleteRoleAndPermissionByPermissionId(List<Long> ids);

    /**
     * 根据角色id验证是否存在
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    int validateIsExistByRoleId(Long roleId, Long permissionId);

    /**
     * 分配权限
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    int assignPermission(List<Long> roleId, Long permissionId);

    /**
     * 移除权限
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    int removePermission(List<Long> roleId, Long permissionId);
}
