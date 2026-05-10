package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 接口限流注解 — 基于 Redis 滑动窗口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 限流key前缀，默认使用方法全限定名
     */
    String key() default "";

    /**
     * 时间窗口（秒），默认60秒
     */
    int time() default 60;

    /**
     * 窗口内最大请求数，默认30次
     */
    int count() default 30;

    /**
     * 提示信息
     */
    String message() default "请求过于频繁，请稍后再试";
}
