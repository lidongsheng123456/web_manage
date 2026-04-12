package com.example.framework.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.example.common.entity.Result;
import com.example.common.exception.BusinessException;
import com.example.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException se) {
        log.error("系统异常:code={},msg={}", se.getCode(), se.getMessage(), se.getCause());
        return Result.error(se.getCode(), se.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException be) {
        log.error("业务异常:code={},msg={}", be.getCode(), be.getMessage(), be.getCause());
        return Result.error(be.getCode(), be.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result doValidException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败: {}", msg);
        return Result.error(400, msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result doHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("请求体解析失败: {}", ex.getMessage());
        return Result.error(400, "请求参数格式错误");
    }

    @ExceptionHandler(NotPermissionException.class)
    public Result doNotPermissionException(NotPermissionException ex) {
        log.warn("无权限访问: {}", ex.getPermission());
        return Result.error(403, "无此操作权限");
    }

    @ExceptionHandler(NotRoleException.class)
    public Result doNotRoleException(NotRoleException ex) {
        log.warn("无角色权限: {}", ex.getRole());
        return Result.error(403, "无此角色权限");
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        log.error("系统异常:msg={}", ex.getMessage(), ex);
        return Result.error(500, "系统异常，请联系管理员");
    }

    @ExceptionHandler(NotLoginException.class)
    public Result handlerNotLoginException(NotLoginException nle) {

        // 判断场景值，定制化异常信息
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token 无效";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token 已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token 已被踢下线";
        } else if (nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            message = "token 已被冻结";
        } else if (nle.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        } else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return Result.error(401, message);
    }

}
