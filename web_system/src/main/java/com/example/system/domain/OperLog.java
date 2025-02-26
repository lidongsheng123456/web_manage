package com.example.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "操作日志ID")
    private Long id;

    @Schema(description = "操作日志标题")
    private String title;

    @Schema(description = "操作业务类型")
    private String businessType;

    @Schema(description = "操作方法")
    private String method;

    @Schema(description = "操作请求方法")
    private String requestMethod;

    @Schema(description = "操作用户名")
    private String operName;

    @Schema(description = "操作路由")
    private String operUrl;

    @Schema(description = "操作参数")
    private String operParam;

    @Schema(description = "操作返回json")
    private String jsonResult;

    @Schema(description = "操作日志状态")
    private Integer status;

    @Schema(description = "操作日志错误信息")
    private String errorMsg;

    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operTime;

    @Schema(description = "操作耗时(毫秒)")
    private Long costTime;
}
