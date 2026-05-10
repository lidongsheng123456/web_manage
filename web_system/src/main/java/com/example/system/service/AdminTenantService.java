package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.Tenant;
import com.github.pagehelper.PageInfo;

public interface AdminTenantService extends IService<Tenant> {
    PageInfo<Tenant> queryTenant(Tenant tenant, Integer currentPage, Integer pageSize);
}
