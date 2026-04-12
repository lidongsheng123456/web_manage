package com.example.aspect;

import com.example.common.enums.BusinessType;
import com.example.system.domain.OperLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LogAspect 相关 — OperLog 构建逻辑单元测试
 * <p>
 * LogAspect.recordLog 内部强依赖 StpUtil / RequestContextHolder 等静态方法，
 * 在 Mockito 4.x + Java 17 环境下无法使用 mockStatic（需要 inline MockMaker，
 * 但该 MockMaker 与项目其他测试不兼容）。
 * 因此这里只验证 OperLog 数据组装逻辑是否正确。
 */
@DisplayName("LogAspect OperLog 构建逻辑测试")
class LogAspectTest {

    @Test
    @DisplayName("OperLog 全参构造正常赋值")
    void operLogAllArgs() {
        LocalDateTime now = LocalDateTime.of(2025, 6, 1, 12, 0, 0);
        OperLog log = new OperLog(
                null, "用户管理", BusinessType.INSERT.name,
                "com.example.AdminUserController.addUser()",
                "POST", "admin",
                "http://localhost:8088/admin/user",
                "[User{username=test}]",
                "{\"code\":200}", 0, "", now, 50L
        );

        assertNull(log.getId());
        assertEquals("用户管理", log.getTitle());
        assertEquals("insert", log.getBusinessType());
        assertEquals("com.example.AdminUserController.addUser()", log.getMethod());
        assertEquals("POST", log.getRequestMethod());
        assertEquals("admin", log.getOperName());
        assertEquals("http://localhost:8088/admin/user", log.getOperUrl());
        assertEquals(0, log.getStatus());
        assertEquals("", log.getErrorMsg());
        assertEquals(now, log.getOperTime());
        assertEquals(50L, log.getCostTime());
    }

    @Test
    @DisplayName("OperLog 异常场景 status=1 且包含错误信息")
    void operLogExceptionScenario() {
        LocalDateTime now = LocalDateTime.now();
        OperLog log = new OperLog(
                null, "通知管理", BusinessType.DELETE.name,
                "com.example.AdminNoticeController.deleteNotice()",
                "DELETE", "admin",
                "http://localhost:8088/admin/notice",
                "[ids=[1,2]]",
                "", 1, "NullPointerException", now, 120L
        );

        assertEquals(1, log.getStatus());
        assertEquals("NullPointerException", log.getErrorMsg());
        assertEquals("", log.getJsonResult());
        assertEquals("delete", log.getBusinessType());
    }

    @Test
    @DisplayName("BusinessType 枚举 name 字段对应正确")
    void businessTypeNames() {
        assertEquals("insert", BusinessType.INSERT.name);
        assertEquals("update", BusinessType.UPDATE.name);
        assertEquals("delete", BusinessType.DELETE.name);
        assertEquals("export", BusinessType.EXPORT.name);
        assertEquals("other", BusinessType.OTHER.name);
    }

    @Test
    @DisplayName("OperLog 无参构造 + setter 赋值")
    void operLogNoArgsAndSetters() {
        OperLog log = new OperLog();
        log.setTitle("测试");
        log.setStatus(0);
        log.setCostTime(10L);
        log.setOperName("");

        assertEquals("测试", log.getTitle());
        assertEquals(0, log.getStatus());
        assertEquals(10L, log.getCostTime());
        assertEquals("", log.getOperName());
    }
}
