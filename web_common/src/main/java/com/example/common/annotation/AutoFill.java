package com.example.common.annotation;

import com.example.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义注解,用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD) //(元注解)指定注解可以用于方法上
@Retention(RetentionPolicy.RUNTIME) //（元注解）指定注解在运行时保留
@Documented //（元注解）指定注解包含在 JavaDoc 中
public @interface AutoFill {
    // 数据库操作类型, UPDATE, INSERT
    // 没有默认值的话使用该注解的时候必须传对应的返回值类型
    // 设置默认值例:BusinessType value() default BusinessType.INSERT;
    BusinessType value();
}

