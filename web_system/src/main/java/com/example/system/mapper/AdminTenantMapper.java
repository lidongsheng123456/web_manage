package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.annotation.TenantIgnore;
import com.example.system.domain.Tenant;

import java.util.List;

public interface AdminTenantMapper extends BaseMapper<Tenant> {

    @TenantIgnore
    void batchDeleteTenant(List<Long> ids);
}
