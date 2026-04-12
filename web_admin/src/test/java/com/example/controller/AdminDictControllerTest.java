package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.admin.AdminDictController;
import com.example.system.domain.Dict;
import com.example.system.service.AdminDictService;
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
 * AdminDictController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminDictController 字典管理接口测试")
class AdminDictControllerTest {

    @Mock
    private AdminDictService adminDictService;

    private AdminDictController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminDictController(adminDictService);
    }

    @Test
    @DisplayName("addDict 成功")
    void addDictSuccess() {
        Dict dict = new Dict();

        Result result = controller.addDict(dict);

        verify(adminDictService).addDict(dict);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("batchDeleteDict 成功")
    void batchDeleteDictSuccess() {
        List<Long> ids = List.of(1L, 2L);

        Result result = controller.batchDeleteDict(ids);

        verify(adminDictService).batchDeleteDict(ids);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updateDict 成功")
    void updateDictSuccess() {
        Dict dict = new Dict();

        Result result = controller.updateDict(dict);

        verify(adminDictService).updateDict(dict);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryDict 分页查询")
    void queryDict() {
        Dict dict = new Dict();
        PageInfo<Dict> page = new PageInfo<>(List.of());
        when(adminDictService.queryDict(dict, 1, 10)).thenReturn(page);

        Result<PageInfo<Dict>> result = controller.queryDict(dict, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryDictById 成功")
    void queryDictById() {
        Dict dict = new Dict();
        dict.setId(1L);
        when(adminDictService.queryDictById(1L)).thenReturn(dict);

        Result<Dict> result = controller.queryDictById(1L);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("exportExcel 导出 Excel")
    void exportExcel() throws Exception {
        when(adminDictService.queryAllDict()).thenReturn(List.of());

        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.exportExcel(response);

        verify(adminDictService).queryAllDict();
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                response.getContentType());
    }
}
