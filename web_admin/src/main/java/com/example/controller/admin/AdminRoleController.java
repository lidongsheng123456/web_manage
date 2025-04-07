package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignRoleDTO;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.AdminRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "角色相关接口")
@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @Operation(summary = "新增角色")
    @SaCheckPermission("admin:role:add")
    @Log(title = "新增角色", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addRole(@RequestBody Role role) {
        adminRoleService.addRole(role);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除角色")
    @SaCheckPermission("admin:role:delete")
    @Log(title = "删除角色", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable Long id) {
        adminRoleService.deleteRole(id);
        return Result.success();
    }

    /**
     * 批量删除角色
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除角色")
    @SaCheckPermission("admin:role:delete")
    @Log(title = "批量删除角色", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteRole(@RequestParam List<Long> ids) {
        adminRoleService.batchDeleteRole(ids);
        return Result.success();
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @Operation(summary = "修改角色")
    @SaCheckPermission("admin:role:update")
    @Log(title = "修改角色", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateRole(@RequestBody Role role) {
        adminRoleService.updateRole(role);
        return Result.success();
    }

    /**
     * 查询角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询角色")
    @SaCheckPermission("admin:role:query")
    @GetMapping
    public Result<PageInfo<Role>> queryRole(Role role,
                                            @RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Role> page = adminRoleService.queryRole(role, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询角色")
    @SaCheckPermission("admin:role:query")
    @GetMapping("/{id}")
    public Result<Role> queryRoleById(@PathVariable Long id) {
        Role role = adminRoleService.queryRoleById(id);
        return Result.success(role);
    }

    /**
     * 查询没有分配当前角色的用户
     *
     * @param userVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询没有分配当前角色的用户")
    @SaCheckPermission("admin:user:query")
    @GetMapping("/notRoleId")
    public Result<PageInfo<UserVo>> queryUserNotRoleId(UserVo userVo,
                                                       @RequestParam(defaultValue = "1") Integer currentPage,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<UserVo> page = adminRoleService.queryUserNotRoleId(userVo, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 查询已经分配当前角色的用户
     *
     * @param userVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询已经分配当前角色的用户")
    @SaCheckPermission("admin:user:query")
    @GetMapping("/role")
    public Result<PageInfo<UserVo>> queryUserByRoleId(UserVo userVo,
                                                      @RequestParam(defaultValue = "1") Integer currentPage,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<UserVo> page = adminRoleService.queryUserByRoleId(userVo, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 分配角色
     *
     * @param assignRoleDTO
     * @return
     */
    @Operation(summary = "分配角色")
    @SaCheckPermission("admin:role:assign")
    @Log(title = "分配角色", businessType = BusinessType.INSERT)
    @PostMapping("/assign")
    public Result assignRole(@RequestBody AssignRoleDTO assignRoleDTO) {
        adminRoleService.assignRole(assignRoleDTO);
        return Result.success();
    }

    /**
     * 移除角色
     *
     * @param assignRoleDTO
     * @return
     */
    @Operation(summary = "移除角色")
    @SaCheckPermission("admin:role:remove")
    @Log(title = "移除角色", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public Result removeRole(@RequestBody AssignRoleDTO assignRoleDTO) {
        adminRoleService.removeRole(assignRoleDTO);
        return Result.success();
    }

    /**
     * 导出角色信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出角色信息")
    @SaCheckPermission("admin:role:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Role> roles = adminRoleService.queryAllRole();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(roles, Role.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("角色列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
