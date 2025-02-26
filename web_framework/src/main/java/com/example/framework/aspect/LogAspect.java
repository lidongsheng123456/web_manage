package com.example.framework.aspect;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.example.common.annotation.Log;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.OperLog;
import com.example.system.domain.User;
import com.example.system.mapper.AdminOperLogMapper;
import com.example.system.mapper.AdminUserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 记录增删改日志，注意不要给文件上传加，因为没有用axios设置统一请求格式，没有token
 */
@Slf4j
@Component
@Aspect
public class LogAspect {
    private final AdminUserMapper adminUserMapper;

    private final AdminOperLogMapper adminOperLogMapper;

    public LogAspect(AdminOperLogMapper adminOperLogMapper, AdminUserMapper adminUserMapper) {
        this.adminOperLogMapper = adminOperLogMapper;
        this.adminUserMapper = adminUserMapper;
    }

    /**
     * 环绕通知可以手动书写原始方法的调用，并拿到返回值
     * 环绕通知依赖形参ProceedingJoinPoint才能实现对原始方法的调用
     * 环绕通知可以隔离原始方法的调用执行
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(log)")
    public Object recordLog(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        long loginId;
        try {
            loginId = StpUtil.getLoginIdAsLong();
        } catch (NotLoginException ne) {
            loginId = 0L;
        }
        User user = adminUserMapper.queryUserById(loginId);

        //1.模块标题
        String title = log.title();

        //2.业务类型
        BusinessType businessType = log.businessType();

        //3.方法名称
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        methodName = className + "." + methodName + "()";

        //4.请求方式
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestMethod = request.getMethod();

        //6.操作人员
        String operatorName;
        if (user != null) {
            operatorName = user.getUsername();
        } else {
            operatorName = "";
        }

        //7.请求URL
        String requestUrl = request.getRequestURL().toString();

        //9.请求参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        //10.返回结果json
        String returnValue = "";

        //11.状态（0：正常 1：异常）
        int status = 0;

        //12.错误信息
        String errorMsg = "";

        //13.操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //14.操作耗时
        //记录方法开始前的时间戳
        Long begin = System.currentTimeMillis();

        try {
            //开始执行切入点方法并拿到切入点方法的返回值
            Object resultValue = joinPoint.proceed();

            //记录方法开始后的时间戳
            Long end = System.currentTimeMillis();

            //记录方法返回值
            returnValue = JSONUtil.toJsonStr(resultValue);

            if (!returnValue.contains("200")) {
                status = 1;
            }

            //记录方法执行总耗时
            Long costTime = end - begin;

            OperLog operLog = new OperLog(null, title, businessType.name, methodName, requestMethod, operatorName, requestUrl, methodParams, returnValue, status, errorMsg, operateTime, costTime);

            try {
                adminOperLogMapper.addOperLog(operLog);
            } catch (MyBatisSystemException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCodeEnum.LOG_ERROR);
            }
            //必须return，这里的返回值是用的原方法的返回值，不return的话就给前端响应了个空响应体
            return resultValue;
        } catch (Throwable throwable) {
            // 异常处理
            Long end = System.currentTimeMillis();
            Long costTime = end - begin;
            returnValue = "";
            errorMsg = throwable.getMessage();
            status = 1;

            OperLog operLog = new OperLog(null, title, businessType.name, methodName, requestMethod, operatorName, requestUrl, methodParams, returnValue, status, errorMsg, operateTime, costTime);

            try {
                adminOperLogMapper.addOperLog(operLog);
            } catch (MyBatisSystemException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCodeEnum.LOG_ERROR);
            }
            throw throwable;
        }
    }

}
