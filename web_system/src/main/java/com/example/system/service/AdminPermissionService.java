package com.example.system.service;

import com.example.system.domain.Permission;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignPermissionDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminPermissionService {
    /**
     * 新增权限
     *
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 批量删除权限
     *
     * @param ids
     */
    void batchDeletePermission(List<Long> ids);

    /**
     * 修改权限
     *
     * @param permission
     */
    void updatePermission(Permission permission);

    /**
     * 查询权限
     *
     * @param permission
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<Permission> queryPermission(Permission permission, Integer currentPage, Integer pageSize);

    /**
     * 根据id查询权限
     *
     * @param id
     * @return
     */
    Permission queryPermissionById(Long id);

    /**
     * 查询全部权限
     *
     * @return
     */
    List<Permission> queryAllPermission();

    /**
     * 分配权限
     *
     * @param assignPermissionDTO
     */
    void assignPermission(AssignPermissionDTO assignPermissionDTO);

    /**
     * 移除权限
     *
     * @param assignPermissionDTO
     */
    void removePermission(AssignPermissionDTO assignPermissionDTO);

    /**
     * 查询没有分配当前权限的角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<Role> queryRoleNotPermissionId(Role role, Integer currentPage, Integer pageSize);

    /**
     * 查询已经分配当前权限的角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<Role> queryRoleByPermissionId(Role role, Integer currentPage, Integer pageSize);
}
