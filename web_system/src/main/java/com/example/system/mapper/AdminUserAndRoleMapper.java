package com.example.system.mapper;

import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface AdminUserAndRoleMapper {
    /**
     * 新增用户和角色关联id
     *
     * @param userId
     * @param roleId
     * @return
     */
    int addUserAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户id删除用户和角色关联id
     *
     * @param userId
     */
    void deleteUserAndRoleByUserId(Long userId);

    /**
     * 根据用户id批量删除用户和角色关联id
     *
     * @param ids
     */
    void batchDeleteUserAndRoleByUserId(List<Long> ids);

    /**
     * 根据角色id删除用户和角色关联id
     *
     * @param roleId
     */
    void deleteUserAndRoleByRoleId(Long roleId);

    /**
     * 根据角色id批量删除用户和角色关联id
     *
     * @param ids
     */
    void batchDeleteUserAndRoleByRoleId(List<Long> ids);

    /**
     * 分配角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    int assignRole(List<Long> userId, Long roleId);

    /**
     * 根据用户id验证是否存在
     *
     * @param userId
     * @param roleId
     * @return
     */
    int validateIsExistByUserId(Long userId, Long roleId);

    /**
     * 移除角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    int removeRole(List<Long> userId, Long roleId);

    /**
     * 查询用户分配了多少个角色
     *
     * @param userId
     * @return
     */
    @MapKey("user_id")
    List<Map<Long, Long>> getUserAndRoleByUserId(Long userId);
}
