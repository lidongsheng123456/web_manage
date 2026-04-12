package com.example.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.exception.SystemException;
import com.example.framework.web.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalExceptionHandler 单元测试
 */
@DisplayName("GlobalExceptionHandler 全局异常处理测试")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("SystemException 应返回对应 code 和 msg")
    void handleSystemException() {
        SystemException se = new SystemException(ResultCodeEnum.SYSTEM_ERROR);
        Result result = handler.doSystemException(se);

        assertEquals(500, result.getCode());
        assertEquals("系统异常", result.getMsg());
    }

    @Test
    @DisplayName("BusinessException 应返回业务错误码")
    void handleBusinessException() {
        BusinessException be = new BusinessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        Result result = handler.doBusinessException(be);

        assertEquals(5004, result.getCode());
        assertEquals("用户不存在", result.getMsg());
    }

    @Test
    @DisplayName("MethodArgumentNotValidException 应拼接校验失败信息")
    void handleValidException() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "username", "用户名不能为空"));
        bindingResult.addError(new FieldError("target", "password", "密码不能为空"));

        MethodParameter methodParameter = new MethodParameter(
                this.getClass().getDeclaredMethod("handleValidException"), -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        Result result = handler.doValidException(ex);

        assertEquals(400, result.getCode());
        assertTrue(result.getMsg().contains("用户名不能为空"));
        assertTrue(result.getMsg().contains("密码不能为空"));
    }

    @Test
    @DisplayName("HttpMessageNotReadableException 应返回 400")
    void handleHttpMessageNotReadable() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("bad body");
        Result result = handler.doHttpMessageNotReadableException(ex);

        assertEquals(400, result.getCode());
        assertEquals("请求参数格式错误", result.getMsg());
    }

    @Test
    @DisplayName("NotPermissionException 应返回 403")
    void handleNotPermission() {
        NotPermissionException ex = new NotPermissionException("admin:user:add", "admin");
        Result result = handler.doNotPermissionException(ex);

        assertEquals(403, result.getCode());
        assertEquals("无此操作权限", result.getMsg());
    }

    @Test
    @DisplayName("NotRoleException 应返回 403")
    void handleNotRole() {
        NotRoleException ex = new NotRoleException("super-admin", "admin");
        Result result = handler.doNotRoleException(ex);

        assertEquals(403, result.getCode());
        assertEquals("无此角色权限", result.getMsg());
    }

    @Test
    @DisplayName("通用 Exception 应返回 500")
    void handleGenericException() {
        Exception ex = new RuntimeException("unexpected error");
        Result result = handler.doException(ex);

        assertEquals(500, result.getCode());
        assertEquals("系统异常，请联系管理员", result.getMsg());
    }

    @Test
    @DisplayName("NotLoginException NOT_TOKEN 场景")
    void handleNotLoginNotToken() {
        NotLoginException nle = NotLoginException.newInstance("", NotLoginException.NOT_TOKEN, NotLoginException.NOT_TOKEN_MESSAGE, null);
        Result result = handler.handlerNotLoginException(nle);

        assertEquals(401, result.getCode());
        assertEquals("未能读取到有效 token", result.getMsg());
    }

    @Test
    @DisplayName("NotLoginException INVALID_TOKEN 场景")
    void handleNotLoginInvalidToken() {
        NotLoginException nle = NotLoginException.newInstance("", NotLoginException.INVALID_TOKEN, NotLoginException.INVALID_TOKEN_MESSAGE, null);
        Result result = handler.handlerNotLoginException(nle);

        assertEquals(401, result.getCode());
        assertEquals("token 无效", result.getMsg());
    }

    @Test
    @DisplayName("NotLoginException TOKEN_TIMEOUT 场景")
    void handleNotLoginTokenTimeout() {
        NotLoginException nle = NotLoginException.newInstance("", NotLoginException.TOKEN_TIMEOUT, NotLoginException.TOKEN_TIMEOUT_MESSAGE, null);
        Result result = handler.handlerNotLoginException(nle);

        assertEquals(401, result.getCode());
        assertEquals("token 已过期", result.getMsg());
    }
}
