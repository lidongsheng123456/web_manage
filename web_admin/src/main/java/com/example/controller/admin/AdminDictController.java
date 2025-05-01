package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.Dict;
import com.example.system.service.AdminDictService;
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

@Tag(name = "字典相关接口")
@RestController
@RequestMapping("/admin/dict")
@RequiredArgsConstructor
public class AdminDictController {

    private final AdminDictService adminDictService;

    /**
     * 新增字典
     *
     * @param dict
     * @return
     */
    @Operation(summary = "新增字典")
    @SaCheckPermission("admin:dict:add")
    @Log(title = "新增字典", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addDict(@RequestBody Dict dict) {
        adminDictService.addDict(dict);
        return Result.success();
    }

    /**
     * 批量删除字典
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除字典")
    @SaCheckPermission("admin:dict:delete")
    @Log(title = "批量删除字典", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteDict(@RequestParam List<Long> ids) {
        adminDictService.batchDeleteDict(ids);
        return Result.success();
    }

    /**
     * 修改字典
     *
     * @param dict
     * @return
     */
    @Operation(summary = "修改字典")
    @SaCheckPermission("admin:dict:update")
    @Log(title = "修改字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateDict(@RequestBody Dict dict) {
        adminDictService.updateDict(dict);
        return Result.success();
    }

    /**
     * 查询字典
     *
     * @param dict
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询字典")
    @SaCheckPermission("admin:dict:query")
    @GetMapping
    public Result<PageInfo<Dict>> queryDict(Dict dict,
                                            @RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Dict> page = adminDictService.queryDict(dict, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询字典
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询字典")
    @SaCheckPermission("admin:dict:query")
    @GetMapping("/{id}")
    public Result<Dict> queryDictById(@PathVariable Long id) {
        Dict dict = adminDictService.queryDictById(id);
        return Result.success(dict);
    }

    /**
     * 导出字典信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出字典信息")
    @SaCheckPermission("admin:dict:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Dict> notices = adminDictService.queryAllDict();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(notices, Dict.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("字典列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
