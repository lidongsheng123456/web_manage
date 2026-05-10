package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.Tenant;
import com.example.system.service.AdminTenantService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "租户管理接口")
@RestController
@RequestMapping("/admin/tenant")
@RequiredArgsConstructor
public class AdminTenantController {

    private final AdminTenantService adminTenantService;

    @Operation(summary = "新增租户")
    @SaCheckPermission("admin:tenant:add")
    @Log(title = "新增租户", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addTenant(@RequestBody @Valid Tenant tenant) {
        tenant.setCreateTime(LocalDateTime.now());
        tenant.setUpdateTime(LocalDateTime.now());
        adminTenantService.save(tenant);
        return Result.success();
    }

    @Operation(summary = "批量删除租户")
    @SaCheckPermission("admin:tenant:delete")
    @Log(title = "删除租户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public Result deleteTenant(@PathVariable List<Long> ids) {
        // 禁止删除默认租户(id=1)
        if (ids.contains(1L)) {
            return Result.error(400, "禁止删除默认租户");
        }
        adminTenantService.getBaseMapper().delete(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Tenant>()
                        .in(Tenant::getId, ids)
        );
        return Result.success();
    }

    @Operation(summary = "修改租户")
    @SaCheckPermission("admin:tenant:update")
    @Log(title = "修改租户", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateTenant(@RequestBody @Valid Tenant tenant) {
        if (tenant.getId() == null) {
            return Result.error(400, "租户ID不能为空");
        }
        tenant.setUpdateTime(LocalDateTime.now());
        adminTenantService.updateById(tenant);
        return Result.success();
    }

    @Operation(summary = "分页查询租户")
    @SaCheckPermission("admin:tenant:query")
    @GetMapping
    public Result queryTenant(Tenant tenant,
                              @RequestParam(defaultValue = "1") Integer currentPage,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Tenant> pageInfo = adminTenantService.queryTenant(tenant, currentPage, pageSize);
        return Result.success(pageInfo);
    }

    @Operation(summary = "根据ID查询租户")
    @SaCheckPermission("admin:tenant:query")
    @GetMapping("/{id}")
    public Result queryTenantById(@PathVariable Long id) {
        return Result.success(adminTenantService.getById(id));
    }

    @Operation(summary = "查询全部租户（下拉选用）")
    @SaCheckPermission("admin:tenant:query")
    @GetMapping("/all")
    public Result queryAllTenant() {
        return Result.success(adminTenantService.list());
    }

    @Operation(summary = "导出租户")
    @SaCheckPermission("admin:tenant:export")
    @Log(title = "导出租户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Tenant> list = adminTenantService.list();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(list, Tenant.class);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                URLEncoder.encode("租户数据.xlsx", StandardCharsets.UTF_8));

        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
