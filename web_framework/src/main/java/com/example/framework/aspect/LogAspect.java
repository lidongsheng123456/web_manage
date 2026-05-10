package com.example.framework.aspect;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.example.common.annotation.Log;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.OperLog;
import com.example.system.domain.User;
import com.example.framework.notify.NotificationHelper;
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

    private final NotificationHelper notificationHelper;

    public LogAspect(AdminOperLogMapper adminOperLogMapper, AdminUserMapper adminUserMapper, NotificationHelper notificationHelper) {
        this.adminOperLogMapper = adminOperLogMapper;
        this.adminUserMapper = adminUserMapper;
        this.notificationHelper = notificationHelper;
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
    @Around("@annotation(logAnnotation)")
    public Object recordLog(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        long loginId;
        try {
            loginId = StpUtil.getLoginIdAsLong();
        } catch (NotLoginException ne) {
            loginId = 0L;
        }
        User user = adminUserMapper.queryUserById(loginId);

        //1.模块标题
        String title = logAnnotation.title();

        //2.业务类型
        BusinessType businessType = logAnnotation.businessType();

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

        //7.1 IP地址
        String operIp = getIpAddr(request);

        //7.2 浏览器 & OS
        String uaStr = request.getHeader("User-Agent");
        String browserName = "Unknown";
        String osName = "Unknown";
        if (uaStr != null) {
            UserAgent ua = UserAgentUtil.parse(uaStr);
            if (ua != null) {
                browserName = ua.getBrowser() != null ? ua.getBrowser().getName() + " " + ua.getVersion() : "Unknown";
                osName = ua.getOs() != null ? ua.getOs().getName() : "Unknown";
            }
        }

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

            try {
                Object code = JSONUtil.parseObj(returnValue).get("code");
                if (code != null && !"200".equals(code.toString())) {
                    status = 1;
                }
            } catch (Exception ignored) {
                // 返回值不是JSON时忽略状态判断
            }

            //记录方法执行总耗时
            Long costTime = end - begin;

            OperLog operLog = buildOperLog(title, businessType, methodName, requestMethod, operatorName,
                    operIp, browserName, osName, requestUrl, methodParams, returnValue, status, errorMsg, operateTime, costTime);

            try {
                adminOperLogMapper.addOperLog(operLog);
            } catch (MyBatisSystemException e) {
                log.error("操作日志写入失败", e);
                throw new BusinessException(ResultCodeEnum.LOG_ERROR);
            }
            return resultValue;
        } catch (Throwable throwable) {
            // 异常处理
            Long end = System.currentTimeMillis();
            Long costTime = end - begin;
            returnValue = "";
            errorMsg = throwable.getMessage();
            status = 1;

            OperLog operLog = buildOperLog(title, businessType, methodName, requestMethod, operatorName,
                    operIp, browserName, osName, requestUrl, methodParams, returnValue, status, errorMsg, operateTime, costTime);

            try {
                adminOperLogMapper.addOperLog(operLog);
            } catch (MyBatisSystemException e) {
                log.error("操作日志写入失败", e);
                throw new BusinessException(ResultCodeEnum.LOG_ERROR);
            }
            // 操作异常时向超级管理员发送预警站内信
            try {
                notificationHelper.warnAdmin("操作异常预警：" + title,
                        "操作人：" + operatorName + "\n方法：" + methodName + "\n错误：" + errorMsg);
            } catch (Exception ignored) {
            }
            throw throwable;
        }
    }

    private OperLog buildOperLog(String title, BusinessType businessType, String method, String requestMethod,
                                 String operName, String operIp, String browser, String os,
                                 String operUrl, String operParam, String jsonResult,
                                 int status, String errorMsg, LocalDateTime operTime, Long costTime) {
        OperLog operLog = new OperLog();
        operLog.setTitle(title);
        operLog.setBusinessType(businessType.name);
        operLog.setMethod(method);
        operLog.setRequestMethod(requestMethod);
        operLog.setOperName(operName);
        operLog.setOperIp(operIp);
        operLog.setOperLocation("");
        operLog.setBrowser(browser);
        operLog.setOs(os);
        operLog.setOperUrl(operUrl);
        operLog.setOperParam(operParam);
        operLog.setJsonResult(jsonResult);
        operLog.setStatus(status);
        operLog.setErrorMsg(errorMsg);
        operLog.setOperTime(operTime);
        operLog.setCostTime(costTime);
        return operLog;
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
