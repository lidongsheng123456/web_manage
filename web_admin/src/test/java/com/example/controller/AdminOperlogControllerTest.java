package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.admin.AdminOperlogController;
import com.example.system.domain.OperLog;
import com.example.system.service.AdminOperLogService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminOperlogController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminOperlogController 日志管理接口测试")
class AdminOperlogControllerTest {

    @Mock
    private AdminOperLogService adminOperLogService;

    private AdminOperlogController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminOperlogController(adminOperLogService);
    }

    @Test
    @DisplayName("batchDeleteOperLog 成功")
    void batchDeleteOperLogSuccess() {
        List<Long> ids = List.of(1L, 2L);

        Result result = controller.batchDeleteOperLog(ids);

        verify(adminOperLogService).batchDeleteOperLog(ids);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updateOperLog 成功")
    void updateOperLogSuccess() {
        OperLog operLog = new OperLog();
        operLog.setId(1L);

        Result result = controller.updateOperLog(operLog);

        verify(adminOperLogService).updateOperLog(operLog);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryOperLog 分页查询")
    void queryOperLog() {
        OperLog operLog = new OperLog();
        PageInfo<OperLog> page = new PageInfo<>(List.of());
        when(adminOperLogService.queryOperLog(operLog, 1, 10)).thenReturn(page);

        Result<PageInfo<OperLog>> result = controller.queryOperLog(operLog, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryOperLogById 成功")
    void queryOperLogById() {
        OperLog operLog = new OperLog();
        operLog.setId(1L);
        when(adminOperLogService.queryOperLogById(1L)).thenReturn(operLog);

        Result<OperLog> result = controller.queryOperLogById(1L);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("exportExcel 导出 Excel")
    void exportExcel() throws Exception {
        when(adminOperLogService.queryAllOperLog()).thenReturn(List.of());

        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.exportExcel(response);

        verify(adminOperLogService).queryAllOperLog();
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                response.getContentType());
    }
}
