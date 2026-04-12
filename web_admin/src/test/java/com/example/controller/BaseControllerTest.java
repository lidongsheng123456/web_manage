package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.common.BaseController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseController 单元测试
 */
@DisplayName("BaseController 基础接口测试")
class BaseControllerTest {

    private final BaseController controller = new BaseController();

    @Test
    @DisplayName("hello 应返回成功")
    void helloSuccess() {
        Result result = controller.hello();

        assertEquals(200, result.getCode());
        assertEquals("访问成功", result.getData());
    }
}
