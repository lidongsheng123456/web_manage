package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 标记此注解的 Mapper 方法将跳过租户过滤
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantIgnore {
}
