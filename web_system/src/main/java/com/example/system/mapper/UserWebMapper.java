package com.example.system.mapper;

import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;

public interface UserWebMapper {
    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 前台注册用户
     *
     * @param User
     * @return
     */
    int register(UserDto User);

    /**
     * 修改个人信息
     *
     * @return
     */
    int updatePerson(User user);

    /**
     * 根据用户id查询
     *
     * @param loginIdAsLong
     * @return
     */
    User selectByUserId(Long loginIdAsLong);


    /**
     * 查询当前登录用户信息
     *
     * @param id
     * @return
     */
    UserVo queryCurrentUser(Long id);
}
