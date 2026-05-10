package com.example.framework.interceptor;

import com.example.common.annotation.TenantIgnore;
import com.example.common.context.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * MyBatis 租户拦截器 — 自动为 SELECT 语句追加 tenant_id 条件
 * <p>
 * 使用方式:
 * 1. 在需要租户隔离的表中添加 tenant_id 列
 * 2. 通过 TenantContext.setTenantId() 设置当前租户
 * 3. 在不需要隔离的 Mapper 方法上标注 @TenantIgnore
 * </p>
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class})
})
public class TenantInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return invocation.proceed();
        }

        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType cmdType = ms.getSqlCommandType();

        // 仅处理 SELECT / UPDATE / DELETE（INSERT 由业务层自行设置 tenant_id）
        if (cmdType != SqlCommandType.SELECT && cmdType != SqlCommandType.UPDATE && cmdType != SqlCommandType.DELETE) {
            return invocation.proceed();
        }

        // 检查 @TenantIgnore
        if (hasTenantIgnore(ms)) {
            return invocation.proceed();
        }

        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = ms.getBoundSql(parameter);
        String originalSql = boundSql.getSql().trim();

        // 安全拼接 tenant_id（Long 类型确保无注入风险）
        String tenantCondition = "tenant_id = " + tenantId.longValue();
        String newSql;
        String lowerSql = originalSql.toLowerCase();
        if (lowerSql.contains("where")) {
            newSql = originalSql + " AND " + tenantCondition;
        } else if (lowerSql.contains("group by") || lowerSql.contains("order by") || lowerSql.contains("limit")) {
            int idx = findFirstKeyword(lowerSql);
            newSql = originalSql.substring(0, idx) + " WHERE " + tenantCondition + " " + originalSql.substring(idx);
        } else {
            newSql = originalSql + " WHERE " + tenantCondition;
        }

        // 用反射替换 SQL
        java.lang.reflect.Field field = BoundSql.class.getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);

        // 使用新的 BoundSql 执行（仅 query 的6参数签名需要替换）
        if (invocation.getArgs().length == 6) {
            invocation.getArgs()[5] = boundSql;
        }

        return invocation.proceed();
    }

    private int findFirstKeyword(String lowerSql) {
        int groupBy = lowerSql.indexOf("group by");
        int orderBy = lowerSql.indexOf("order by");
        int limit = lowerSql.indexOf("limit");
        int min = lowerSql.length();
        if (groupBy > 0 && groupBy < min) min = groupBy;
        if (orderBy > 0 && orderBy < min) min = orderBy;
        if (limit > 0 && limit < min) min = limit;
        return min;
    }

    private boolean hasTenantIgnore(MappedStatement ms) {
        try {
            String id = ms.getId();
            int lastDot = id.lastIndexOf('.');
            String className = id.substring(0, lastDot);
            String methodName = id.substring(lastDot + 1);
            Class<?> clazz = Class.forName(className);
            // 类级别 @TenantIgnore → 整个 Mapper 跳过
            if (clazz.isAnnotationPresent(TenantIgnore.class)) {
                return true;
            }
            // 方法级别 @TenantIgnore
            for (Method method : clazz.getMethods()) {
                if (method.getName().equals(methodName) && method.isAnnotationPresent(TenantIgnore.class)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.debug("检查 TenantIgnore 异常: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // no-op
    }
}
