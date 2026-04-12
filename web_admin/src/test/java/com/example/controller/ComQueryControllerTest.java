package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.common.ComQueryController;
import com.example.system.domain.vo.DictVo;
import com.example.system.service.ComQueryService;
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
 * ComQueryController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ComQueryController 通用查询接口测试")
class ComQueryControllerTest {

    @Mock
    private ComQueryService comQueryService;

    private ComQueryController controller;

    @BeforeEach
    void setUp() {
        controller = new ComQueryController(comQueryService);
    }

    @Test
    @DisplayName("queryDictByType 查询静态字典")
    void queryDictByType() {
        DictVo dictVo = new DictVo("标签", 1, "success");
        when(comQueryService.queryDictByType("gender")).thenReturn(List.of(dictVo));

        Result<List<DictVo>> result = controller.queryDictByType("gender");

        assertEquals(200, result.getCode());
        assertEquals(1, result.getData().size());
        assertEquals("标签", result.getData().get(0).getDictLabel());
    }

    @Test
    @DisplayName("queryDictByType 返回空列表")
    void queryDictByTypeEmpty() {
        when(comQueryService.queryDictByType("unknown")).thenReturn(List.of());

        Result<List<DictVo>> result = controller.queryDictByType("unknown");

        assertEquals(200, result.getCode());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    @DisplayName("queryComQueryByCode 查询动态字典")
    void queryComQueryByCode() {
        DictVo dictVo = new DictVo("动态标签", 2, "info");
        when(comQueryService.queryComQueryByCode("user_status")).thenReturn(List.of(dictVo));

        Result<List<DictVo>> result = controller.queryComQueryByCode("user_status");

        assertEquals(200, result.getCode());
        assertEquals(1, result.getData().size());
        verify(comQueryService).queryComQueryByCode("user_status");
    }
}
