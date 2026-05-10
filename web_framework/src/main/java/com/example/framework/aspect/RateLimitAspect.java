package com.example.framework.aspect;

import com.example.common.annotation.RateLimit;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流切面 — 基于 Redis 计数器
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String ip = getIpAddr(request);
        String key = buildKey(joinPoint, rateLimit, ip);

        Long current = stringRedisTemplate.opsForValue().increment(key);
        if (current != null && current == 1) {
            stringRedisTemplate.expire(key, rateLimit.time(), TimeUnit.SECONDS);
        }

        if (current != null && current > rateLimit.count()) {
            log.warn("接口限流: key={}, count={}/{}", key, current, rateLimit.count());
            throw new BusinessException(ResultCodeEnum.RATE_LIMIT.code, rateLimit.message());
        }

        return joinPoint.proceed();
    }

    private String buildKey(ProceedingJoinPoint joinPoint, RateLimit rateLimit, String ip) {
        String prefix = rateLimit.key();
        if (prefix.isEmpty()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            prefix = method.getDeclaringClass().getName() + "." + method.getName();
        }
        return "rate_limit:" + prefix + ":" + ip;
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
