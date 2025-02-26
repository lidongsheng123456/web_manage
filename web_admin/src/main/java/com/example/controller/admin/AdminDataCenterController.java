package com.example.controller.admin;

import com.example.common.entity.Result;
import com.example.system.domain.vo.DataVo;
import com.example.system.service.AdminDataCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "数据中心相关接口")
@RestController
@RequestMapping("/admin/data")
@Slf4j
public class AdminDataCenterController {
    private final AdminDataCenterService adminDataCenterService;

    public AdminDataCenterController(AdminDataCenterService adminDataCenterService) {
        this.adminDataCenterService = adminDataCenterService;
    }

    /**
     * 查询数据
     *
     * @return
     */
    @Operation(summary = "查询数据")
    @GetMapping
    public Result<DataVo> queryData() {
        DataVo dataVo = adminDataCenterService.queryData();
        return Result.success(dataVo);
    }
}
