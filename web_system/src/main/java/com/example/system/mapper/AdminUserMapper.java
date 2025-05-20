package com.example.system.mapper;

import com.example.system.domain.User;
import com.example.system.domain.vo.UserVo;

import java.util.List;

public interface AdminUserMapper {
    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    int batchDeleteUser(List<Long> ids);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 查询用户
     *
     * @param userVo
     * @return
     */
    List<UserVo> queryUser(UserVo userVo);

    /**
     * 根据id查询用户
     *
     * @param loginUser
     * @return
     */
    User queryUserById(Long loginUser);

    /**
     * 查询全部用户
     *
     * @return
     */
    List<User> queryAllUser();
}
