package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.ComQuery;
import com.example.system.service.AdminComQueryService;
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

@Tag(name = "通用查询相关接口")
@RestController
@RequestMapping("/admin/com-query")
@RequiredArgsConstructor
public class AdminComQueryController {

    private final AdminComQueryService adminComQueryService;

    /**
     * 新增通用查询
     *
     * @param comQuery
     * @return
     */
    @Operation(summary = "新增通用查询")
    @SaCheckPermission("admin:com-query:add")
    @Log(title = "新增通用查询", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addComQuery(@RequestBody ComQuery comQuery) {
        adminComQueryService.addComQuery(comQuery);
        return Result.success();
    }

    /**
     * 批量删除通用查询
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除通用查询")
    @SaCheckPermission("admin:com-query:delete")
    @Log(title = "批量删除通用查询", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteComQuery(@RequestParam List<Long> ids) {
        adminComQueryService.batchDeleteComQuery(ids);
        return Result.success();
    }

    /**
     * 修改通用查询
     *
     * @param comQuery
     * @return
     */
    @Operation(summary = "修改通用查询")
    @SaCheckPermission("admin:com-query:update")
    @Log(title = "修改通用查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateComQuery(@RequestBody ComQuery comQuery) {
        adminComQueryService.updateComQuery(comQuery);
        return Result.success();
    }

    /**
     * 查询通用查询
     *
     * @param comQuery
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询通用查询")
    @SaCheckPermission("admin:com-query:query")
    @GetMapping
    public Result<PageInfo<ComQuery>> queryDict(ComQuery comQuery,
                                                @RequestParam(defaultValue = "1") Integer currentPage,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ComQuery> page = adminComQueryService.queryComQuery(comQuery, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询通用查询
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询通用查询")
    @SaCheckPermission("admin:com-query:query")
    @GetMapping("/{id}")
    public Result<ComQuery> queryComQueryById(@PathVariable Long id) {
        ComQuery comQuery = adminComQueryService.queryComQueryById(id);
        return Result.success(comQuery);
    }

    /**
     * 导出通用查询信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出通用查询信息")
    @SaCheckPermission("admin:com-query:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<ComQuery> comQueries = adminComQueryService.queryAllComQuery();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(comQueries, ComQuery.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("通用查询列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
