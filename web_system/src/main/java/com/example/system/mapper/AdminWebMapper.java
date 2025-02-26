package com.example.system.mapper;

import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminWebMapper {
    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 后台注册用户
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
     * 验证密码正确
     *
     * @param password
     * @return
     */
    List<User> validateFormerPassword(String password, Long loginId);
}
