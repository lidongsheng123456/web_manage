package com.example.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.annotation.Log;
import com.example.common.constants.Constants;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.redis.RedisCache;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.Notice;
import com.example.system.service.AdminNoticeService;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
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

@Tag(name = "通知相关接口")
@RequestMapping("/admin/notice")
@RestController
@RequiredArgsConstructor
public class AdminNoticeController {
    private final AdminNoticeService adminNoticeService;

    private final RedisCache redisCache;

    @AutoFill(BusinessType.INSERT)
    @Operation(summary = "新增通知")
    @SaCheckPermission("admin:notice:add")
    @Log(title = "新增通知", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addNotice(@RequestBody Notice notice) {
        // 过滤敏感字
        notice.setNoticeContent(SensitiveWordBs.newInstance().replace(notice.getNoticeContent(), '*'));
        notice.setCreateUserId(StpUtil.getLoginIdAsLong());
        if (adminNoticeService.save(notice)) {
            return Result.success();
        }
        return Result.error(500, "新增通知失败");
    }

    @Operation(summary = "批量删除通知")
    @SaCheckPermission("admin:notice:delete")
    @Log(title = "批量删除通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteNotice(@RequestParam List<Long> ids) {
        if (adminNoticeService.removeBatchByIds(ids)) {
            ids.forEach(ids1 -> redisCache.deleteObject(Constants.noticeCacheKey + ids1.toString()));
            return Result.success();
        }
        return Result.error(500, "删除通知失败");
    }

    @AutoFill(BusinessType.UPDATE)
    @Operation(summary = "修改通知")
    @SaCheckPermission("admin:notice:update")
    @Log(title = "修改通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateNotice(@RequestBody Notice notice) {
        // 过滤敏感字
        notice.setNoticeContent(SensitiveWordBs.newInstance().replace(notice.getNoticeContent(), '*'));
        if (adminNoticeService.updateById(notice)) {
            redisCache.deleteObject(Constants.noticeCacheKey + notice.getId().toString());
            return Result.success();
        }
        return Result.error(500, "修改通知失败");
    }

    @Operation(summary = "查询通知")
    @SaCheckPermission("admin:notice:query")
    @GetMapping
    public Result<PageInfo<Notice>> queryNotice(Notice notice,
                                                @RequestParam(defaultValue = "1") Integer currentPage,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notice> page = adminNoticeService.queryNotice(notice, currentPage, pageSize);
        return Result.success(page);
    }

    @Operation(summary = "根据id查询通知")
    @SaCheckPermission("admin:notice:query")
    @GetMapping("/{id}")
    public Result<Notice> queryNoticeById(@PathVariable Long id) {
        Notice byId = adminNoticeService.getById(id);
        return Result.success(byId);
    }

    @Operation(summary = "导出通知信息")
    @SaCheckPermission("admin:notice:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Notice> list = adminNoticeService.list();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(list, Notice.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("通知列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
