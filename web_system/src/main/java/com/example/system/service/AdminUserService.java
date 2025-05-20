package com.example.system.service;

import com.example.system.domain.User;
import com.example.system.domain.vo.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminUserService {
    /**
     * 新增用户
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 批量删除用户
     *
     * @param ids
     */
    void batchDeleteUser(List<Long> ids);

    /**
     * 修改用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 查询用户
     *
     * @param userVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<UserVo> queryUser(UserVo userVo, Integer currentPage, Integer pageSize);

    /**
     * 查询全部用户
     *
     * @return
     */
    List<User> queryAllUser();

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    UserVo queryUserById(Long id);
}
