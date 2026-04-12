package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.admin.AdminComQueryController;
import com.example.system.domain.ComQuery;
import com.example.system.service.AdminComQueryService;
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
 * AdminComQueryController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminComQueryController 通用查询接口测试")
class AdminComQueryControllerTest {

    @Mock
    private AdminComQueryService adminComQueryService;

    private AdminComQueryController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminComQueryController(adminComQueryService);
    }

    @Test
    @DisplayName("addComQuery 成功")
    void addComQuerySuccess() {
        ComQuery comQuery = new ComQuery();

        Result result = controller.addComQuery(comQuery);

        verify(adminComQueryService).addComQuery(comQuery);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("batchDeleteComQuery 成功")
    void batchDeleteComQuerySuccess() {
        List<Long> ids = List.of(1L, 2L);

        Result result = controller.batchDeleteComQuery(ids);

        verify(adminComQueryService).batchDeleteComQuery(ids);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updateComQuery 成功")
    void updateComQuerySuccess() {
        ComQuery comQuery = new ComQuery();

        Result result = controller.updateComQuery(comQuery);

        verify(adminComQueryService).updateComQuery(comQuery);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryDict 分页查询")
    void queryDict() {
        ComQuery comQuery = new ComQuery();
        PageInfo<ComQuery> page = new PageInfo<>(List.of());
        when(adminComQueryService.queryComQuery(comQuery, 1, 10)).thenReturn(page);

        Result<PageInfo<ComQuery>> result = controller.queryDict(comQuery, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryComQueryById 成功")
    void queryComQueryById() {
        ComQuery comQuery = new ComQuery();
        comQuery.setId(1L);
        when(adminComQueryService.queryComQueryById(1L)).thenReturn(comQuery);

        Result<ComQuery> result = controller.queryComQueryById(1L);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("exportExcel 导出 Excel")
    void exportExcel() throws Exception {
        when(adminComQueryService.queryAllComQuery()).thenReturn(List.of());

        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.exportExcel(response);

        verify(adminComQueryService).queryAllComQuery();
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                response.getContentType());
    }
}
