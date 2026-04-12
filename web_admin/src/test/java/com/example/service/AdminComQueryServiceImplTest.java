package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.ComQuery;
import com.example.system.mapper.AdminComQueryMapper;
import com.example.system.service.impl.AdminComQueryServiceImpl;
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
 * AdminComQueryServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminComQueryServiceImpl 通用查询服务测试")
class AdminComQueryServiceImplTest {

    @Mock
    private AdminComQueryMapper adminComQueryMapper;

    private AdminComQueryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminComQueryServiceImpl(adminComQueryMapper);
    }

    @Test
    @DisplayName("addComQuery 成功")
    void addComQuerySuccess() {
        ComQuery comQuery = new ComQuery();
        when(adminComQueryMapper.addComQuery(comQuery)).thenReturn(1);

        assertDoesNotThrow(() -> service.addComQuery(comQuery));
        verify(adminComQueryMapper).addComQuery(comQuery);
    }

    @Test
    @DisplayName("batchDeleteComQuery ids 为空应抛出异常")
    void batchDeleteComQueryEmptyIds() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.batchDeleteComQuery(List.of()));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteComQuery 正常删除")
    void batchDeleteComQuerySuccess() {
        List<Long> ids = List.of(1L, 2L);
        when(adminComQueryMapper.batchDeleteComQuery(ids)).thenReturn(2);

        assertDoesNotThrow(() -> service.batchDeleteComQuery(ids));
        verify(adminComQueryMapper).batchDeleteComQuery(ids);
    }

    @Test
    @DisplayName("updateComQuery 成功")
    void updateComQuerySuccess() {
        ComQuery comQuery = new ComQuery();
        when(adminComQueryMapper.updateComQuery(comQuery)).thenReturn(1);

        assertDoesNotThrow(() -> service.updateComQuery(comQuery));
    }

    @Test
    @DisplayName("queryComQueryById id 为空应抛出异常")
    void queryComQueryByIdNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.queryComQueryById(null));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("queryComQueryById 正常查询")
    void queryComQueryByIdSuccess() {
        ComQuery comQuery = new ComQuery();
        comQuery.setId(1L);
        when(adminComQueryMapper.queryComQueryById(1L)).thenReturn(comQuery);

        ComQuery result = service.queryComQueryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("queryAllComQuery 返回列表")
    void queryAllComQuery() {
        when(adminComQueryMapper.queryAllComQuery()).thenReturn(List.of(new ComQuery()));

        List<ComQuery> result = service.queryAllComQuery();

        assertEquals(1, result.size());
    }
}
