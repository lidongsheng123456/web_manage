package com.example.controller.common;

import com.example.common.entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "基础相关接口")
@RestController
public class BaseController {

    /**
     * 基础接口
     *
     * @return
     */
    @Operation(summary = "基础接口")
    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }
}
