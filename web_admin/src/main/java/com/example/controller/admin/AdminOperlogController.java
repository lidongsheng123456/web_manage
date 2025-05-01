package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.entity.Result;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.OperLog;
import com.example.system.service.AdminOperLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "日志相关接口")
@RestController
@RequestMapping("/admin/operLog")
@RequiredArgsConstructor
public class AdminOperlogController {

    private final AdminOperLogService adminOperLogService;

    /**
     * 删除日志
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除日志")
    @SaCheckPermission("admin:operLog:delete")
    @DeleteMapping("/{id}")
    public Result deleteOperLog(@PathVariable Long id) {
        adminOperLogService.deleteOperLog(id);
        return Result.success();
    }

    /**
     * 批量删除日志
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除日志")
    @SaCheckPermission("admin:operLog:delete")
    @DeleteMapping("/batchDelete")
    public Result batchDeleteOperLog(@RequestParam List<Long> ids) {
        adminOperLogService.batchDeleteOperLog(ids);
        return Result.success();
    }

    /**
     * 修改日志
     *
     * @param operlog
     * @return
     */
    @Operation(summary = "修改日志")
    @SaCheckPermission("admin:operLog:update")
    @PutMapping
    public Result updateOperLog(@RequestBody OperLog operlog) {
        adminOperLogService.updateOperLog(operlog);
        return Result.success();
    }

    /**
     * 查询日志
     *
     * @param operLog
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询日志")
    @SaCheckPermission("admin:operLog:query")
    @GetMapping
    public Result<PageInfo<OperLog>> queryOperLog(OperLog operLog,
                                                  @RequestParam(defaultValue = "1") Integer currentPage,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<OperLog> page = adminOperLogService.queryOperLog(operLog, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询日志
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询日志")
    @SaCheckPermission("admin:operLog:query")
    @GetMapping("/{id}")
    public Result<OperLog> queryOperLogById(@PathVariable Long id) {
        OperLog operLog = adminOperLogService.queryOperLogById(id);
        return Result.success(operLog);
    }

    /**
     * 导出日志信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出日志信息")
    @SaCheckPermission("admin:operLog:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<OperLog> operLogs = adminOperLogService.queryAllOperLog();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(operLogs, OperLog.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("操作日志列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
