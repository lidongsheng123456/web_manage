package com.example.system.mapper;

import com.example.system.domain.Permission;
import com.example.system.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminPermissionMapper {
    /**
     * 新增权限
     *
     * @param permission
     * @return
     */
    Integer addPermission(Permission permission);

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    Integer deletePermission(Long id);

    /**
     * 批量删除权限
     *
     * @param ids
     * @return
     */
    Integer batchDeletePermission(List<Long> ids);

    /**
     * 修改权限
     *
     * @param permission
     * @return
     */
    Integer updatePermission(Permission permission);

    /**
     * 查询权限
     *
     * @param permission
     * @return
     */
    List<Permission> queryPermission(Permission permission);

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
     * 查询没有分配当前权限的角色
     *
     * @param role
     * @return
     */
    List<Role> queryRoleNotPermissionId(Role role);

    /**
     * 查询已经分配当前权限的角色
     *
     * @param role
     * @return
     */
    List<Role> queryRoleByPermissionId(Role role);
}
