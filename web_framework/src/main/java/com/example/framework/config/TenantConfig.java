package com.example.framework.config;

import com.example.framework.interceptor.TenantInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 租户隔离配置 — 当 app.tenant.enabled=true 时启用
 */
@Configuration
@ConditionalOnProperty(name = "app.tenant.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class TenantConfig {

    private final List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addInterceptor() {
        TenantInterceptor interceptor = new TenantInterceptor();
        for (SqlSessionFactory sf : sqlSessionFactoryList) {
            sf.getConfiguration().addInterceptor(interceptor);
        }
    }
}
