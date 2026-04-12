package com.example.util;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.util.ServiceUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ServiceUtil 单元测试
 */
@DisplayName("ServiceUtil 工具类测试")
class ServiceUtilTest {

    @Test
    @DisplayName("checkSuccess 影响行数大于0不抛异常")
    void checkSuccessPositive() {
        assertDoesNotThrow(() -> ServiceUtil.checkSuccess(1));
        assertDoesNotThrow(() -> ServiceUtil.checkSuccess(5));
        assertDoesNotThrow(() -> ServiceUtil.checkSuccess(100));
    }

    @Test
    @DisplayName("checkSuccess 影响行数为0抛出 SYSTEM_ERROR")
    void checkSuccessZero() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> ServiceUtil.checkSuccess(0));
        assertEquals(ResultCodeEnum.SYSTEM_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("checkSuccess 负数不抛异常（仅判0）")
    void checkSuccessNegative() {
        assertDoesNotThrow(() -> ServiceUtil.checkSuccess(-1));
    }
}
