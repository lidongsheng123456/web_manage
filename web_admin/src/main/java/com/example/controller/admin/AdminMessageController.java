package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.example.common.entity.Result;
import com.example.system.domain.Message;
import com.example.system.mapper.AdminMessageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "个人消息接口")
@RestController
@RequestMapping("/admin/message")
@RequiredArgsConstructor
public class AdminMessageController {

    private final AdminMessageMapper adminMessageMapper;

    @Operation(summary = "查询当前用户消息列表")
    @GetMapping("/list")
    public Result<List<Message>> list() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<Message> messages = adminMessageMapper.queryMessageByReceiverId(userId);
        return Result.success(messages);
    }

    @Operation(summary = "查询未读消息数量")
    @GetMapping("/unread-count")
    public Result<Long> unreadCount() {
        Long userId = StpUtil.getLoginIdAsLong();
        Long count = adminMessageMapper.countUnread(userId);
        return Result.success(count);
    }

    @Operation(summary = "标记消息已读")
    @PutMapping("/read/{id}")
    public Result markAsRead(@PathVariable Long id) {
        adminMessageMapper.markAsRead(id);
        return Result.success();
    }

    @Operation(summary = "全部标记已读")
    @PutMapping("/read-all")
    public Result markAllAsRead() {
        Long userId = StpUtil.getLoginIdAsLong();
        adminMessageMapper.markAllAsRead(userId);
        return Result.success();
    }

    @Operation(summary = "发送消息(管理员)")
    @SaCheckPermission("admin:notice:add")
    @PostMapping("/send")
    public Result send(@RequestBody Message message) {
        message.setSenderId(StpUtil.getLoginIdAsLong());
        adminMessageMapper.addMessage(message);
        return Result.success();
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/batchDelete")
    public Result batchDelete(@RequestParam List<Long> ids) {
        adminMessageMapper.batchDeleteMessage(ids);
        return Result.success();
    }
}
