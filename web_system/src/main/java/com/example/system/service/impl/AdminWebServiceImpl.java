package com.example.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.constants.Constants;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminWebMapper;
import com.example.system.service.AdminWebService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AdminWebServiceImpl implements AdminWebService {

    private final AdminWebMapper adminWebMapper;
    private final AdminRbacMapper adminRbacMapper;

    /**
     * 登录后台
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

        User user = adminWebMapper.selectByUsername(username);

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
        userVo.setPermissions(adminRbacMapper.getPermissionList(userVo.getId()));
        userVo.setRoles(adminRbacMapper.getRoleList(userVo.getId()));

        StpUtil.login(userVo.getId());
        return userVo;
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
        Long loginIdAsLong = StpUtil.getLoginIdAsLong();

        if (ObjectUtil.isEmpty(loginIdAsLong)) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        if (ObjectUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }

        user.setId(loginIdAsLong);

        if (adminWebMapper.updatePerson(user) == 0) {
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
        Long loginIdAsLong = StpUtil.getLoginIdAsLong();

        if (ObjectUtil.isEmpty(loginIdAsLong)) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        String password = DigestUtils.md5DigestAsHex(formerPassword.getBytes());

        User user = adminWebMapper.selectByUserId(loginIdAsLong);
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
        UserVo userVo = adminWebMapper.queryCurrentUser(StpUtil.getLoginIdAsLong());
        userVo.setPermissions(adminRbacMapper.getPermissionList(userVo.getId()));
        userVo.setRoles(adminRbacMapper.getRoleList(userVo.getId()));
        return userVo;
    }
}
