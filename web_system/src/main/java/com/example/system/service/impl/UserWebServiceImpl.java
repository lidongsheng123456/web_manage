package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.constants.Constants;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.util.StpUserUtil;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.UserWebMapper;
import com.example.system.service.UserWebService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserWebServiceImpl implements UserWebService {

    private final UserWebMapper userWebMapper;

    /**
     * 登录前台
     *
     * @param userDto
     * @param session
     * @return
     */
    @Override
    public UserVo login(UserDto userDto, HttpSession session) {
        if (ObjectUtil.isEmpty(userDto.getUsername()) || ObjectUtil.isEmpty(userDto.getPassword()) || ObjectUtil.isEmpty(userDto.getCode())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        String username = userDto.getUsername();
        String password = userDto.getPassword();


        if (validateCaptcha(userDto.getCode(), session)) {
            throw new BusinessException(ResultCodeEnum.CAPTCHA_ERROR);
        }

        User user = userWebMapper.selectByUsername(username);

        //空返回true isEmpty会检查String list array 的长度是否为0 其他对象则检查是否为null
        if (ObjectUtil.isEmpty(user)) {
            throw new BusinessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new BusinessException(ResultCodeEnum.USER_User_ERROR);
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);

        StpUserUtil.login(userVo.getId());
        return userVo;
    }

    /**
     * 前台注册用户
     *
     * @param userDto
     * @param session
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    @Transactional
    public void register(UserDto userDto, HttpSession session) {
        if (ObjectUtil.isEmpty(userDto.getUsername()) || ObjectUtil.isEmpty(userDto.getPassword()) || ObjectUtil.isEmpty(userDto.getCode())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        if (validateCaptcha(userDto.getCode(), session)) {
            throw new BusinessException(ResultCodeEnum.CAPTCHA_ERROR);
        }

        //1. 注册判断数据库是否存在当前用户名的数据
        User User = userWebMapper.selectByUsername(userDto.getUsername());
        //2. 用户不为空则代表当前用户名重复，不能注册，抛出异常
        if (ObjectUtil.isNotNull(User)) {
            throw new BusinessException(ResultCodeEnum.USER_EXIST_ERROR);
        }

        if (ObjectUtil.isNull(userDto.getPassword())) {
            userDto.setPassword(DigestUtils.md5DigestAsHex(Constants.DEFAULT_PAD.getBytes()));
        }

        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));

        int i = userWebMapper.register(userDto);

        if (i == 0) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 验证验证码
     *
     * @param captcha
     * @param session
     * @return
     */
    public Boolean validateCaptcha(String captcha, HttpSession session) {
        // 获取存储的验证码和生成时间
        String code = (String) session.getAttribute(Constants.CAPTCHA_KEY);
        Date createTime = (Date) session.getAttribute(Constants.CAPTCHA_DATE);
        System.out.println("用户验证码->" + captcha);
        System.out.println("正确验证码->" + code);
        // 判断验证码是否正确(验证码一般忽略大小写)
        if (captcha.equalsIgnoreCase(code)) {
            // 判断验证码是否过时
            return createTime == null || System.currentTimeMillis() - createTime.getTime() > Constants.EXPIRATION_TIME;
        }
        return true;
    }

    /**
     * 修改个人信息
     *
     * @param user
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updatePerson(User user) {
        Long loginIdAsLong = StpUserUtil.getLoginIdAsLong();

        if (ObjectUtil.isEmpty(loginIdAsLong)) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        if (ObjectUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }

        user.setId(loginIdAsLong);

        if (userWebMapper.updatePerson(user) == 0) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 验证密码正确
     *
     * @param formerPassword
     */
    @Override
    public void validateFormerPassword(String formerPassword) {
        Long loginIdAsLong = StpUserUtil.getLoginIdAsLong();

        if (ObjectUtil.isEmpty(loginIdAsLong)) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        String password = DigestUtils.md5DigestAsHex(formerPassword.getBytes());

        User user = userWebMapper.selectByUserId(loginIdAsLong);
        if (!user.getPassword().equals(password)) {
            throw new BusinessException(ResultCodeEnum.PASSWORD_ERROR);
        }
    }

    /**
     * 查询当前登录用户信息
     *
     * @return
     */
    @Override
    public UserVo queryCurrentUser() {
        UserVo userVo = userWebMapper.queryCurrentUser(StpUserUtil.getLoginIdAsLong());
        return userVo;
    }
}
