package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.user.UserHomeController;
import com.example.system.domain.Notice;
import com.example.system.service.UserHomeService;
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
 * UserHomeController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserHomeController 用户主页接口测试")
class UserHomeControllerTest {

    @Mock
    private UserHomeService userHomeService;

    private UserHomeController controller;

    @BeforeEach
    void setUp() {
        controller = new UserHomeController(userHomeService);
    }

    @Test
    @DisplayName("queryNotice 返回通知列表")
    void queryNoticeSuccess() {
        Notice notice = new Notice();
        Notice n1 = new Notice();
        n1.setId(1L);
        n1.setNoticeTitle("通知1");
        when(userHomeService.queryNotice(notice)).thenReturn(List.of(n1));

        Result result = controller.queryNotice(notice);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryNotice 返回空列表")
    void queryNoticeEmpty() {
        Notice notice = new Notice();
        when(userHomeService.queryNotice(notice)).thenReturn(List.of());

        Result result = controller.queryNotice(notice);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryNotice 调用服务层")
    void queryNoticeCallsService() {
        Notice notice = new Notice();
        when(userHomeService.queryNotice(notice)).thenReturn(List.of());

        controller.queryNotice(notice);

        verify(userHomeService).queryNotice(notice);
    }
}
