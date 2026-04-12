package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.OperLog;
import com.example.system.mapper.AdminOperLogMapper;
import com.example.system.service.impl.AdminOperLogServiceImpl;
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
 * AdminOperLogServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminOperLogServiceImpl 操作日志服务测试")
class AdminOperLogServiceImplTest {

    @Mock
    private AdminOperLogMapper adminOperLogMapper;

    private AdminOperLogServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminOperLogServiceImpl(adminOperLogMapper);
    }

    @Test
    @DisplayName("batchDeleteOperLog ids 为空应抛出异常")
    void batchDeleteOperLogEmptyIds() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.batchDeleteOperLog(List.of()));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteOperLog 正常删除")
    void batchDeleteOperLogSuccess() {
        List<Long> ids = List.of(1L, 2L);
        when(adminOperLogMapper.batchDeleteOperLog(ids)).thenReturn(2);

        assertDoesNotThrow(() -> service.batchDeleteOperLog(ids));
        verify(adminOperLogMapper).batchDeleteOperLog(ids);
    }

    @Test
    @DisplayName("updateOperLog id 为空应抛出异常")
    void updateOperLogNullId() {
        OperLog operLog = new OperLog();
        // id is null

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.updateOperLog(operLog));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("updateOperLog 正常修改")
    void updateOperLogSuccess() {
        OperLog operLog = new OperLog();
        operLog.setId(1L);
        when(adminOperLogMapper.updateOperLog(operLog)).thenReturn(1);

        assertDoesNotThrow(() -> service.updateOperLog(operLog));
        verify(adminOperLogMapper).updateOperLog(operLog);
    }

    @Test
    @DisplayName("queryOperLogById id 为空应抛出异常")
    void queryOperLogByIdNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.queryOperLogById(null));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("queryOperLogById 正常查询")
    void queryOperLogByIdSuccess() {
        OperLog operLog = new OperLog();
        operLog.setId(1L);
        when(adminOperLogMapper.queryOperLogById(1L)).thenReturn(operLog);

        OperLog result = service.queryOperLogById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("queryAllOperLog 返回列表")
    void queryAllOperLog() {
        when(adminOperLogMapper.queryAllOperLog()).thenReturn(List.of(new OperLog()));

        List<OperLog> result = service.queryAllOperLog();

        assertEquals(1, result.size());
    }
}
