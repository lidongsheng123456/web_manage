package com.example.controller.common;

import com.example.common.entity.Result;
import com.example.system.domain.vo.DictVo;
import com.example.system.service.ComQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "通用查询相关接口")
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class ComQueryController {
    private final ComQueryService comQueryService;

    /**
     * 查询静态字典
     *
     * @param dictType
     * @return
     */
    @Operation(summary = "查询静态字典")
    @GetMapping("/query-dict/{dictType}")
    public Result<List<DictVo>> queryDictByType(@PathVariable String dictType) {
        List<DictVo> dictVo = comQueryService.queryDictByType(dictType);
        return Result.success(dictVo);
    }

    /**
     * 查询动态字典
     *
     * @param code
     * @return
     */
    @Operation(summary = "查询动态字典")
    @GetMapping("/query-com-query/{code}")
    public Result<List<DictVo>> queryComQueryByCode(@PathVariable String code) {
        List<DictVo> dictVo = comQueryService.queryComQueryByCode(code);
        return Result.success(dictVo);
    }
}
