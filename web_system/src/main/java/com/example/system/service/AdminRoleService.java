package com.example.system.service;

import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignRoleDTO;
import com.example.system.domain.vo.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminRoleService {
    /**
     * 新增角色
     *
     * @param role
     */
    void addRole(Role role);

    /**
     * 批量删除角色
     *
     * @param ids
     */
    void batchDeleteRole(List<Long> ids);

    /**
     * 修改角色
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 查询角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<Role> queryRole(Role role, Integer currentPage, Integer pageSize);

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    Role queryRoleById(Long id);

    /**
     * 查询全部角色
     *
     * @return
     */
    List<Role> queryAllRole();

    /**
     * 分配角色
     *
     * @param assignRoleDTO
     */
    void assignRole(AssignRoleDTO assignRoleDTO);

    /**
     * 移除角色
     *
     * @param assignRoleDTO
     */
    void removeRole(AssignRoleDTO assignRoleDTO);

    /**
     * 查询没有分配当前角色的用户
     *
     * @param userVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<UserVo> queryUserNotRoleId(UserVo userVo, Integer currentPage, Integer pageSize);

    /**
     * 查询已经分配当前角色的用户
     *
     * @param userVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<UserVo> queryUserByRoleId(UserVo userVo, Integer currentPage, Integer pageSize);
}
