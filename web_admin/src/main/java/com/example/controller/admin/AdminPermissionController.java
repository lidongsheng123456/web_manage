package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.Permission;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignPermissionDTO;
import com.example.system.service.AdminPermissionService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "访问权限相关接口")
@RestController
@RequestMapping("/admin/permission")
@Slf4j
public class AdminPermissionController {
    private final AdminPermissionService adminPermissionService;

    public AdminPermissionController(AdminPermissionService adminPermissionService) {
        this.adminPermissionService = adminPermissionService;
    }

    /**
     * 新增权限
     *
     * @param permission
     * @return
     */
    @Operation(summary = "新增权限")
    @SaCheckPermission("admin:permission:add")
    @Log(title = "新增权限", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addPermission(@RequestBody Permission permission) {
        adminPermissionService.addPermission(permission);
        return Result.success();
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除权限")
    @SaCheckPermission("admin:permission:delete")
    @Log(title = "删除权限", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public Result deletePermission(@PathVariable Long id) {
        adminPermissionService.deletePermission(id);
        return Result.success();
    }

    /**
     * 批量删除权限
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除权限")
    @SaCheckPermission("admin:permission:delete")
    @Log(title = "批量删除权限", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeletePermission(@RequestParam List<Long> ids) {
        adminPermissionService.batchDeletePermission(ids);
        return Result.success();
    }

    /**
     * 修改权限
     *
     * @param permission
     * @return
     */
    @Operation(summary = "修改权限")
    @SaCheckPermission("admin:permission:update")
    @Log(title = "修改权限", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updatePermission(@RequestBody Permission permission) {
        adminPermissionService.updatePermission(permission);
        return Result.success();
    }

    /**
     * 查询权限
     *
     * @param permission
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询权限")
    @SaCheckPermission("admin:permission:query")
    @GetMapping
    public Result<PageInfo<Permission>> queryPermission(Permission permission,
                                                        @RequestParam(defaultValue = "1") Integer currentPage,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Permission> page = adminPermissionService.queryPermission(permission, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询权限
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询权限")
    @SaCheckPermission("admin:permission:query")
    @GetMapping("/{id}")
    public Result<Permission> queryPermissionById(@PathVariable Long id) {
        Permission permission = adminPermissionService.queryPermissionById(id);
        return Result.success(permission);
    }

    /**
     * 查询没有分配当前权限的角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询没有分配当前权限的角色")
    @SaCheckPermission("admin:permission:query")
    @GetMapping("/NotPermissionId")
    public Result<PageInfo<Role>> queryRoleNotPermissionId(Role role,
                                                           @RequestParam(defaultValue = "1") Integer currentPage,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Role> page = adminPermissionService.queryRoleNotPermissionId(role, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 查询已经分配当前权限的角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询已经分配当前权限的角色")
    @SaCheckPermission("admin:permission:query")
    @GetMapping("/permission")
    public Result<PageInfo<Role>> queryRoleByPermissionId(Role role,
                                                          @RequestParam(defaultValue = "1") Integer currentPage,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Role> page = adminPermissionService.queryRoleByPermissionId(role, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 分配权限
     *
     * @param assignPermissionDTO
     * @return
     */
    @Operation(summary = "分配权限")
    @SaCheckPermission("admin:permission:assign")
    @Log(title = "分配权限", businessType = BusinessType.INSERT)
    @PostMapping("/assign")
    public Result assignPermission(@RequestBody AssignPermissionDTO assignPermissionDTO) {
        adminPermissionService.assignPermission(assignPermissionDTO);
        return Result.success();
    }

    /**
     * 移除权限
     *
     * @param assignPermissionDTO
     * @return
     */
    @Operation(summary = "移除权限")
    @SaCheckPermission("admin:permission:remove")
    @Log(title = "移除权限", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public Result removePermission(@RequestBody AssignPermissionDTO assignPermissionDTO) {
        adminPermissionService.removePermission(assignPermissionDTO);
        return Result.success();
    }

    /**
     * 导出权限信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出权限信息")
    @SaCheckPermission("admin:permission:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Permission> permissions = adminPermissionService.queryAllPermission();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(permissions, Permission.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("权限列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
