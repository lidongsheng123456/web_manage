package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.config.AppConfig;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.util.StpUserUtil;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.common.redis.RedisCache;
import com.example.system.mapper.UserWebMapper;
import com.example.system.service.UserWebService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.crypto.digest.BCrypt;

@Service
@RequiredArgsConstructor
public class UserWebServiceImpl implements UserWebService {

    private final UserWebMapper userWebMapper;
    private final AppConfig appConfig;
    private final RedisCache redisCache;

    /**
     * 登录前台
     *
     * @param userDto
     * @param session
     * @return
     */
    @Override
    public UserVo login(UserDto userDto, HttpSession session) {
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

        //密码比对 BCrypt
        if (!BCrypt.checkpw(password, user.getPassword())) {
            //密码错误
            throw new BusinessException(ResultCodeEnum.USER_PASSWORD_ERROR);
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
            userDto.setPassword(appConfig.getDefaultPassword());
        }

        userDto.setPassword(BCrypt.hashpw(userDto.getPassword()));

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
        // 从 Redis 获取验证码
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        String code = redisCache.getCacheObject(captchaKey);
        // 校验后立即删除，防止重放攻击
        redisCache.deleteObject(captchaKey);
        // 判断验证码是否正确(验证码一般忽略大小写)
        return code == null || !captcha.equalsIgnoreCase(code);
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
            user.setPassword(BCrypt.hashpw(user.getPassword()));
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

        User user = userWebMapper.selectByUserId(loginIdAsLong);
        if (!BCrypt.checkpw(formerPassword, user.getPassword())) {
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
