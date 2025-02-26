package com.example.framework.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.common.entity.Result;
import com.example.common.exception.BusinessException;
import com.example.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException se) {
        se.printStackTrace();
        log.error("系统异常:code={},msg={}", se.getCode(), se.getMessage(), se.getCause());
        return Result.error(se.getCode(), se.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException be) {
        be.printStackTrace();
        log.error("业务异常:code={},msg={}", be.getCode(), be.getMessage(), be.getCause());
        return Result.error(be.getCode(), be.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        ex.printStackTrace();
        // getCause 方法是 Throwable 类中的一个方法，用于获取当前异常的原因
        log.error("异常:msg={}", ex.getMessage(), ex);
        return Result.error(500, ex.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public Result handlerNotLoginException(NotLoginException nle) {
        // 打印堆栈，以供调试
        nle.printStackTrace();

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
