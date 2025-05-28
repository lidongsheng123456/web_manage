package com.example.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.AutoFill;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.FrontUser;
import com.example.system.domain.vo.FrontUserVo;
import com.example.system.service.AdminFrontUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2025-05-28
 */
@Tag(name = "前台用户相关接口")
@RequestMapping("/admin/front-user")
@RestController
@RequiredArgsConstructor
public class AdminFrontUserController {
    private final AdminFrontUserService adminFrontUserService;

    @AutoFill(BusinessType.INSERT)
    @Operation(summary = "新增前台用户")
    @SaCheckPermission("admin:front-user:add")
    @Log(title = "新增前台用户", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addFrontUser(@RequestBody FrontUser frontUser) {
        if (adminFrontUserService.save(frontUser)) {
            return Result.success();
        }
        return Result.error(500, "新增前台用户失败");
    }

    @Operation(summary = "批量删除前台用户")
    @SaCheckPermission("admin:front-user:delete")
    @Log(title = "批量删除前台用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteFrontUser(@RequestParam List<Long> ids) {
        if (adminFrontUserService.removeBatchByIds(ids)) {
            return Result.success();
        }
        return Result.error(500, "删除前台用户失败");
    }

    @AutoFill(BusinessType.UPDATE)
    @Operation(summary = "修改前台用户")
    @SaCheckPermission("admin:front-user:update")
    @Log(title = "修改前台用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateFrontUser(@RequestBody FrontUser frontUser) {
        if (adminFrontUserService.updateById(frontUser)) {
            return Result.success();
        }
        return Result.error(500, "修改前台用户失败");
    }

    @Operation(summary = "查询前台用户")
    @SaCheckPermission("admin:front-user:query")
    @GetMapping
    public Result<PageInfo<FrontUserVo>> queryFrontUser(FrontUser frontUser,
                                                        @RequestParam(defaultValue = "1") Integer currentPage,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<FrontUserVo> page = adminFrontUserService.queryFrontUser(frontUser, currentPage, pageSize);
        return Result.success(page);
    }

    @Operation(summary = "根据id查询前台用户")
    @SaCheckPermission("admin:front-user:query")
    @GetMapping("/{id}")
    public Result<FrontUserVo> queryFrontUserById(@PathVariable Long id) {
        FrontUser byId = adminFrontUserService.getById(id);
        FrontUserVo frontUserVo = new FrontUserVo();
        BeanUtils.copyProperties(byId, frontUserVo);
        return Result.success(frontUserVo);
    }

    @Operation(summary = "导出前台用户信息")
    @SaCheckPermission("admin:front-user:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<FrontUser> list = adminFrontUserService.list();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(list, FrontUser.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("前台用户列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
