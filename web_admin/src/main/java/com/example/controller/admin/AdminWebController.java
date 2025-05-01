package com.example.controller.admin;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.interface_constants.Constants;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.AdminWebService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Tag(name = "web相关接口")
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminWebController {

    private final AdminWebService adminWebService;

    /**
     * 登录后台
     *
     * @param userDto
     * @param session
     * @return
     */
    @Operation(summary = "登录后台")
    @Log(title = "登录后台", businessType = BusinessType.OTHER)
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody UserDto userDto, HttpSession session) {
        UserVo User = adminWebService.login(userDto, session);
        return Result.success(User);
    }

    /**
     * 后台注册用户
     *
     * @param userDto
     * @param session
     * @return
     */
    @Operation(summary = "后台注册用户")
    @Log(title = "后台注册用户", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto, HttpSession session) {
        adminWebService.register(userDto, session);
        return Result.success();
    }

    /**
     * 获取验证码
     *
     * @param session
     * @param response
     */
    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) {
        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(Constants.CAPTCHA_WIDTH, Constants.CAPTCHA_HEIGHT);
        // 设置返回数据类型
        response.setContentType("image/jpeg");
        // 禁止使用缓存
        response.setHeader("Pragma", "No-cache");
        try {
            // 输出到页面
            lineCaptcha.write(response.getOutputStream());
            // 将 生成的验证码 和 验证码生成时间 存储到session中
            session.setAttribute(Constants.CAPTCHA_KEY, lineCaptcha.getCode());
            session.setAttribute(Constants.CAPTCHA_DATE, new Date());
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 退出后台
     *
     * @return
     */
    @Operation(summary = "退出后台")
    @Log(title = "退出后台", businessType = BusinessType.FORCE)
    @GetMapping("/logout")
    public Result logout() {
        Long loginIdAsLong = StpUtil.getLoginIdAsLong();

        if (ObjectUtil.isEmpty(loginIdAsLong)) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        StpUtil.logout(loginIdAsLong);

        return Result.success();
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @Operation(summary = "修改个人信息")
    @Log(title = "修改个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/person")
    public Result updatePerson(@RequestBody User user) {
        adminWebService.updatePerson(user);
        return Result.success();
    }

    /**
     * 验证密码正确
     *
     * @param formerPassword
     * @return
     */
    @Operation(summary = "验证密码正确")
    @Log(title = "验证密码正确", businessType = BusinessType.OTHER)
    @PostMapping("/validate/formerPassword")
    public Result validateFormerPassword(String formerPassword) {
        adminWebService.validateFormerPassword(formerPassword);
        return Result.success();
    }
}
