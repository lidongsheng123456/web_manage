package com.example.entity;

import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Result 统一响应体单元测试
 */
@DisplayName("Result 统一响应体测试")
class ResultTest {

    @Test
    @DisplayName("success() 无数据")
    void successNoData() {
        Result<Void> result = Result.success();

        assertEquals(200, result.getCode());
        assertEquals("成功", result.getMsg());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("success(data) 有数据")
    void successWithData() {
        Result<String> result = Result.success("hello");

        assertEquals(200, result.getCode());
        assertEquals("成功", result.getMsg());
        assertEquals("hello", result.getData());
    }

    @Test
    @DisplayName("error() 默认系统错误")
    void errorDefault() {
        Result<Void> result = Result.error();

        assertEquals(500, result.getCode());
        assertEquals("系统异常", result.getMsg());
    }

    @Test
    @DisplayName("error(code, msg) 自定义错误")
    void errorCustom() {
        Result<Void> result = Result.error(400, "参数错误");

        assertEquals(400, result.getCode());
        assertEquals("参数错误", result.getMsg());
    }

    @Test
    @DisplayName("error(ResultCodeEnum) 枚举错误")
    void errorEnum() {
        Result<Void> result = Result.error(ResultCodeEnum.USER_NOT_EXIST_ERROR);

        assertEquals(5004, result.getCode());
        assertEquals("用户不存在", result.getMsg());
    }

    @Test
    @DisplayName("全参构造和 getter/setter")
    void allArgsConstructor() {
        Result<String> result = new Result<>(200, "ok", "data");

        assertEquals(200, result.getCode());
        assertEquals("ok", result.getMsg());
        assertEquals("data", result.getData());
    }
}
