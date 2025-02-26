package com.example.framework.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.MethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面,实现公共字段自动填充处理逻辑
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 注解切入点
     */
    @Pointcut("@annotation(com.example.common.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 前置通知,在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        BusinessType BusinessType = autoFill.value();//获得数据库操作类型

        //获取到当前被拦截的方法的参数 -- 实体对象
        Object[] args = joinPoint.getArgs();
        if (ObjectUtil.isEmpty(args)) {
            return;
        }

        //获取对象数组的第一个参数对象
        Object pojo = args[0];

        log.info("开始公共字段的填充:{}", args);

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();

        //根据当前不同的操作类型,为对应的属性赋值通过反射赋值
        if (BusinessType == BusinessType.INSERT) {
            try {
                //反射获取到方法形参对象的set方法，并指定方法的参数类型为LocalDateTime
                Method setCreateTime = pojo.getClass().getDeclaredMethod(MethodEnum.SET_CREATE_TIME.str, LocalDateTime.class);
                Method setUpdateTime = pojo.getClass().getDeclaredMethod(MethodEnum.SET_UPDATE_TIME.str, LocalDateTime.class);

                // 通过反射为对象属性赋值
                // 1.反射调用方法时，需要指定方法是在哪个对象实例上调用的，pojo就是这个对象的实例
                // 2.需要的参数，需要跟实体类中的属性类型一致
                setCreateTime.invoke(pojo, now);
                setUpdateTime.invoke(pojo, now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (BusinessType == BusinessType.UPDATE) {
            try {
                //反射获取到方法形参对象上的set方法，并指定方法的参数类型为LocalDateTime
                Method setUpdateTime = pojo.getClass().getDeclaredMethod(MethodEnum.SET_UPDATE_TIME.str, LocalDateTime.class);

                // 通过反射为对象属性赋值
                // 1.反射调用方法时，需要指定方法是在哪个对象实例上调用的，pojo就是这个对象的实例
                // 2.需要的参数，需要跟实体类中的属性类型一致
                setUpdateTime.invoke(pojo, now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.info("完成公共字段的填充:{}", args);
    }
}
