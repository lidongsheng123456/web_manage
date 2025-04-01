package com.example.system.mapper;

import com.example.system.domain.Role;
import com.example.system.domain.vo.UserVo;

import java.util.List;

public interface AdminRoleMapper {
    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    int deleteRole(Long id);

    /**
     * 批量删除角色
     *
     * @param ids
     * @return
     */
    int batchDeleteRole(List<Long> ids);

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 查询角色
     *
     * @param role
     * @return
     */
    List<Role> queryRole(Role role);

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
     * 查询没有分配当前角色的用户
     *
     * @param user
     * @return
     */
    List<UserVo> queryUserNotRoleId(UserVo user);

    /**
     * 查询已经分配当前角色的用户
     *
     * @param user
     * @return
     */
    List<UserVo> queryUserByRoleId(UserVo user);
}
