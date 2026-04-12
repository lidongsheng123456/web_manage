package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Dict;
import com.example.system.mapper.AdminDictMapper;
import com.example.system.service.impl.AdminDictServiceImpl;
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
 * AdminDictServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminDictServiceImpl 字典管理服务测试")
class AdminDictServiceImplTest {

    @Mock
    private AdminDictMapper adminDictMapper;

    private AdminDictServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminDictServiceImpl(adminDictMapper);
    }

    @Test
    @DisplayName("addDict 成功")
    void addDictSuccess() {
        Dict dict = new Dict();
        when(adminDictMapper.addDict(dict)).thenReturn(1);

        assertDoesNotThrow(() -> service.addDict(dict));
        verify(adminDictMapper).addDict(dict);
    }

    @Test
    @DisplayName("batchDeleteDict ids 为空应抛出异常")
    void batchDeleteDictEmptyIds() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.batchDeleteDict(List.of()));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteDict 正常删除")
    void batchDeleteDictSuccess() {
        List<Long> ids = List.of(1L, 2L);
        when(adminDictMapper.batchDeleteDict(ids)).thenReturn(2);

        assertDoesNotThrow(() -> service.batchDeleteDict(ids));
        verify(adminDictMapper).batchDeleteDict(ids);
    }

    @Test
    @DisplayName("updateDict 成功")
    void updateDictSuccess() {
        Dict dict = new Dict();
        when(adminDictMapper.updateDict(dict)).thenReturn(1);

        assertDoesNotThrow(() -> service.updateDict(dict));
    }

    @Test
    @DisplayName("queryDictById id 为空应抛出异常")
    void queryDictByIdNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.queryDictById(null));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("queryDictById 正常查询")
    void queryDictByIdSuccess() {
        Dict dict = new Dict();
        dict.setId(1L);
        when(adminDictMapper.queryDictById(1L)).thenReturn(dict);

        Dict result = service.queryDictById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("queryAllDict 返回列表")
    void queryAllDict() {
        when(adminDictMapper.queryAllDict()).thenReturn(List.of(new Dict()));

        List<Dict> result = service.queryAllDict();

        assertEquals(1, result.size());
    }
}
