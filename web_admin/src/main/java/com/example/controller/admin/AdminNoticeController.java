package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.common.util.ExcelExportUtil;
import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import com.example.system.service.AdminNoticeService;
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

@Tag(name = "通知相关接口")
@RestController
@RequestMapping("/admin/notice")
@Slf4j
public class AdminNoticeController {
    private final AdminNoticeService adminNoticeService;

    public AdminNoticeController(AdminNoticeService adminNoticeService) {
        this.adminNoticeService = adminNoticeService;
    }

    /**
     * 新增通知
     *
     * @param notice
     * @return
     */
    @Operation(summary = "新增通知")
    @SaCheckPermission("admin:notice:add")
    @Log(title = "新增通知", businessType = BusinessType.INSERT)
    @PostMapping
    public Result addNotice(@RequestBody Notice notice) {
        adminNoticeService.addNotice(notice);
        return Result.success();
    }

    /**
     * 删除通知
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除通知")
    @SaCheckPermission("admin:notice:delete")
    @Log(title = "删除通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public Result deleteNotice(@PathVariable Long id) {
        adminNoticeService.deleteNotice(id);
        return Result.success();
    }

    /**
     * 批量删除通知
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除通知")
    @SaCheckPermission("admin:notice:delete")
    @Log(title = "批量删除通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result batchDeleteNotice(@RequestParam List<Long> ids) {
        adminNoticeService.batchDeleteNotice(ids);
        return Result.success();
    }

    /**
     * 修改通知
     *
     * @param notice
     * @return
     */
    @Operation(summary = "修改通知")
    @SaCheckPermission("admin:notice:update")
    @Log(title = "修改通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result updateNotice(@RequestBody Notice notice) {
        adminNoticeService.updateNotice(notice);
        return Result.success();
    }

    /**
     * 查询通知
     *
     * @param noticeVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询通知")
    @SaCheckPermission("admin:notice:query")
    @GetMapping
    public Result<PageInfo<NoticeVo>> queryNotice(NoticeVo noticeVo,
                                                  @RequestParam(defaultValue = "1") Integer currentPage,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<NoticeVo> page = adminNoticeService.queryNotice(noticeVo, currentPage, pageSize);
        return Result.success(page);
    }

    /**
     * 根据id查询通知
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询通知")
    @SaCheckPermission("admin:notice:query")
    @GetMapping("/{id}")
    public Result<Notice> queryNoticeById(@PathVariable Long id) {
        Notice notice = adminNoticeService.queryNoticeById(id);
        return Result.success(notice);
    }

    /**
     * 导出通知信息
     *
     * @param response
     * @throws IOException
     */
    @Operation(summary = "导出通知信息")
    @SaCheckPermission("admin:notice:export")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Notice> notices = adminNoticeService.queryAllNotice();
        byte[] excelBytes = ExcelExportUtil.exportToExcel(notices, Notice.class);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("通知列表.xlsx", StandardCharsets.UTF_8));

        // 将 Excel 文件写入响应输出流
        response.getOutputStream().write(excelBytes);
        response.getOutputStream().flush();
    }
}
