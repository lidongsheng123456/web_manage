package com.example.framework.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.example.common.context.TenantContext;
import com.example.system.mapper.AdminWebMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 租户过滤器 — 从当前登录用户自动提取 tenant_id 并设置到 TenantContext
 * <p>
 * 超级管理员 (tenant_id = 1 且角色含 super_admin) 不设置 tenant_id，可查看全部数据
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TenantFilter implements Filter {

    private final AdminWebMapper adminWebMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // 仅对已登录的后台用户设置租户上下文
            if (StpUtil.isLogin()) {
                Long userId = StpUtil.getLoginIdAsLong();
                com.example.system.domain.User user = adminWebMapper.selectByUserId(userId);
                if (user != null && user.getTenantId() != null) {
                    // 默认租户(id=1)视为超级租户，不做隔离过滤
                    if (user.getTenantId() != 1L) {
                        TenantContext.setTenantId(user.getTenantId());
                    }
                }
            }
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
