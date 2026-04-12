package com.example.controller;

import com.example.common.config.AppConfig;
import com.example.common.entity.Result;
import com.example.common.redis.RedisCache;
import com.example.controller.admin.AdminNoticeController;
import com.example.system.domain.Notice;
import com.example.system.service.AdminNoticeService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminNoticeController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminNoticeController 通知管理接口测试")
class AdminNoticeControllerTest {

    @Mock
    private AdminNoticeService adminNoticeService;

    @Mock
    private RedisCache redisCache;

    private AppConfig appConfig;
    private AdminNoticeController controller;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        controller = new AdminNoticeController(adminNoticeService, redisCache, appConfig);
    }

    @Test
    @DisplayName("batchDeleteNotice 成功时清除缓存")
    void batchDeleteNoticeSuccess() {
        List<Long> ids = List.of(1L, 2L);
        when(adminNoticeService.removeBatchByIds(ids)).thenReturn(true);

        Result result = controller.batchDeleteNotice(ids);

        assertEquals(200, result.getCode());
        verify(redisCache).deleteObject("notice:1");
        verify(redisCache).deleteObject("notice:2");
    }

    @Test
    @DisplayName("batchDeleteNotice 失败时不清除缓存")
    void batchDeleteNoticeFail() {
        List<Long> ids = List.of(1L);
        when(adminNoticeService.removeBatchByIds(ids)).thenReturn(false);

        Result result = controller.batchDeleteNotice(ids);

        assertEquals(500, result.getCode());
        verify(redisCache, never()).deleteObject(anyString());
    }

    @Test
    @DisplayName("batchDeleteNotice 使用自定义 noticePrefix")
    void batchDeleteNoticeCustomPrefix() {
        appConfig.getCache().setNoticePrefix("n:");
        List<Long> ids = List.of(5L);
        when(adminNoticeService.removeBatchByIds(ids)).thenReturn(true);

        controller.batchDeleteNotice(ids);

        verify(redisCache).deleteObject("n:5");
    }

    @Test
    @DisplayName("updateNotice 成功时清除缓存")
    void updateNoticeSuccess() {
        Notice notice = new Notice();
        notice.setId(3L);
        notice.setNoticeContent("内容");
        when(adminNoticeService.updateById(notice)).thenReturn(true);

        Result result = controller.updateNotice(notice);

        assertEquals(200, result.getCode());
        verify(redisCache).deleteObject("notice:3");
    }

    @Test
    @DisplayName("updateNotice 失败时不清除缓存")
    void updateNoticeFail() {
        Notice notice = new Notice();
        notice.setId(3L);
        notice.setNoticeContent("内容");
        when(adminNoticeService.updateById(notice)).thenReturn(false);

        Result result = controller.updateNotice(notice);

        assertEquals(500, result.getCode());
        verify(redisCache, never()).deleteObject(anyString());
    }

    @Test
    @DisplayName("queryNotice 分页查询")
    void queryNotice() {
        Notice notice = new Notice();
        PageInfo<Notice> page = new PageInfo<>(List.of());
        when(adminNoticeService.queryNotice(notice, 1, 10)).thenReturn(page);

        Result<PageInfo<Notice>> result = controller.queryNotice(notice, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryNoticeById 根据ID查询")
    void queryNoticeById() {
        Notice notice = new Notice();
        notice.setId(1L);
        notice.setNoticeTitle("测试通知");
        when(adminNoticeService.getById(1L)).thenReturn(notice);

        Result<Notice> result = controller.queryNoticeById(1L);

        assertEquals(200, result.getCode());
        assertEquals("测试通知", result.getData().getNoticeTitle());
    }
}
