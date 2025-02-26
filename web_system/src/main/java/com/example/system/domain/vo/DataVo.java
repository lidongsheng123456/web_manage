package com.example.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户每天总数列表")
    private List<DataCountTypeVo> user;

    @Schema(description = "通知每天总数列表")
    private List<DataCountTypeVo> notice;

    @Schema(description = "操作日志每天总数列表")
    private List<DataCountTypeVo> operLog;

    @Schema(description = "访问权限每天总数列表")
    private List<DataCountTypeVo> permission;
}
