package com.example.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "通用查询ID")
    private Long id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @NotBlank(message = "代码不能为空")
    @Schema(description = "代码")
    private String code;

    @NotBlank(message = "SQL语句不能为空")
    @Schema(description = "sql语句")
    private String customSql;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
