package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.framework.notify.NotificationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "实时通知接口")
@RequestMapping("/admin/notify")
@RestController
@RequiredArgsConstructor
public class AdminNotifyController {

    private final NotificationHelper notificationHelper;

    /**
     * 发送全局通知（WebSocket实时推送 + 站内信持久化给所有用户）
     */
    @Operation(summary = "发送全局通知")
    @SaCheckPermission("admin:notice:add")
    @Log(title = "发送实时通知", businessType = BusinessType.INSERT)
    @PostMapping("/send")
    public Result sendNotification(@RequestBody Map<String, String> body) {
        String title = body.getOrDefault("title", "系统通知");
        String content = body.getOrDefault("content", "");
        Long senderId = StpUtil.getLoginIdAsLong();
        notificationHelper.broadcast(senderId, title, content);
        return Result.success();
    }
}
