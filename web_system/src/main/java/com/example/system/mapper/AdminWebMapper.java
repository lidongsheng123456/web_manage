package com.example.system.mapper;

import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;

public interface AdminWebMapper {
    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);

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
