package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.domain.Tenant;
import com.example.system.mapper.AdminTenantMapper;
import com.example.system.service.AdminTenantService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTenantServiceImpl extends ServiceImpl<AdminTenantMapper, Tenant> implements AdminTenantService {
    private final AdminTenantMapper adminTenantMapper;

    @Override
    public PageInfo<Tenant> queryTenant(Tenant tenant, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<Tenant>()
                .like(tenant.getTenantName() != null && !tenant.getTenantName().isEmpty(), Tenant::getTenantName, tenant.getTenantName())
                .eq(tenant.getStatus() != null, Tenant::getStatus, tenant.getStatus());
        List<Tenant> list = adminTenantMapper.selectList(wrapper);
        return PageInfo.of(list);
    }
}
