package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.User;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.AdminUserService;
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

@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Operation(summary = "新增用户")
    @SaCheckPermission("admin:user:add")
    @Log(title = "新增用户", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addUser(@RequestBody User user) {
        adminUserService.addUser(user);
        return Result.success();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除用户")
    @SaCheckPermission("admin:user:delete")
    @Log(title = "删除用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return Result.success();
    }

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除用户")
    @SaCheckPermission("admin:user:delete")
    @Log(title = "批量删除用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteUser(@RequestParam List<Long> ids) {
        adminUserService.batchDeleteUser(ids);
        return Result.success();
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @Operation(summary = "修改用户")
    @SaCheckPermission("admin:user:update")
    @Log(title = "修改用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateUser(@RequestBody User user) {
        adminUserService.updateUser(user);
        return Result.success();
    }

    /**
     * 查询用户
     *
     * @param userVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询用户")
    @SaCheckPermission("admin:user:query")
    @GetMapping
    public Result<PageInfo<UserVo>> queryUser(UserVo userVo,
                                              @RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<UserVo> page = adminUserService.queryUser(userVo, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询用户")
    @SaCheckPermission("admin:user:query")
    @GetMapping("/{id}")
    public Result<UserVo> queryUserById(@PathVariable Long id) {
        UserVo userVo = adminUserService.queryUserById(id);
        return Result.success(userVo);
    }

    /**
     * 查询当前登录用户信息
     *
     * @return
     */
    @Operation(summary = "查询当前登录用户信息")
    @SaCheckPermission("admin:person:query")
    @GetMapping("/current")
    public Result<UserVo> queryCurrentUser() {
        UserVo userVo = adminUserService.queryCurrentUser();
        return Result.success(userVo);
    }

    /**
     * 导出用户信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出用户信息")
    @SaCheckPermission("admin:user:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<User> users = adminUserService.queryAllUser();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(users, User.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("用户列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
