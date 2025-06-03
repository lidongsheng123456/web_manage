package com.example.system.service;

import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import jakarta.servlet.http.HttpSession;

public interface AdminWebService {
    /**
     * 登录后台
     *
     * @param userDto
     * @param session
     * @return
     */
    UserVo login(UserDto userDto, HttpSession session);

    /**
     * 修改个人信息
     *
     * @param user
     */
    void updatePerson(User user);

    /**
     * 验证密码正确
     *
     * @param formerPassword
     */
    void validateFormerPassword(String formerPassword);

    /**
     * 查询当前登录用户信息
     *
     * @return
     */
    UserVo queryCurrentUser();
}
