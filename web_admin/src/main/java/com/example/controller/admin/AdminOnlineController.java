package com.example.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.example.common.annotation.Log;
import com.example.common.entity.Result;
import com.example.common.enums.BusinessType;
import com.example.framework.notify.NotificationHelper;
import com.example.system.mapper.AdminWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "在线用户管理")
@RequestMapping("/admin/online")
@RestController
@RequiredArgsConstructor
public class AdminOnlineController {

    private final AdminWebMapper adminWebMapper;
    private final NotificationHelper notificationHelper;

    /**
     * 查询在线用户列表
     */
    @Operation(summary = "查询在线用户列表")
    @SaCheckPermission("admin:user:query")
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list() {
        List<String> sessionIds = StpUtil.searchSessionId("", 0, -1, false);
        List<Map<String, Object>> result = new ArrayList<>();
        for (String sessionId : sessionIds) {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            if (session == null) continue;
            Object loginId = session.getLoginId();
            if (loginId == null) continue;

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("tokenValue", StpUtil.getTokenValueByLoginId(loginId));
            map.put("loginId", loginId);

            // 查询用户名
            try {
                Map<String, Object> user = adminWebMapper.selectUserBaseById(Long.valueOf(loginId.toString()));
                if (user != null) {
                    map.put("username", user.get("username"));
                    map.put("name", user.get("name"));
                    map.put("imgUrl", user.get("img_url"));
                }
            } catch (Exception ignored) {
            }

            map.put("loginTime", new Date(session.getCreateTime()));
            result.add(map);
        }
        return Result.success(result);
    }

    /**
     * 强制踢下线
     */
    @Operation(summary = "强制踢下线")
    @SaCheckPermission("admin:user:delete")
    @Log(title = "强制踢下线", businessType = BusinessType.FORCE)
    @DeleteMapping("/kick/{loginId}")
    public Result kick(@PathVariable Long loginId) {
        Long operatorId = StpUtil.getLoginIdAsLong();
        StpUtil.kickout(loginId);
        notificationHelper.sendMessage(operatorId, loginId, "system", "强制下线通知", "您已被管理员强制踢下线，如有疑问请联系管理员。");
        return Result.success();
    }
}
