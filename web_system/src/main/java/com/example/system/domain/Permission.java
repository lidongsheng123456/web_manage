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
public class Permission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "访问权限ID")
    private Long id;

    @Schema(description = "访问权限代码")
    private String permissionCode;

    @Schema(description = "访问权限名称")
    private String permissionName;

    @Schema(description = "访问权限描述")
    private String description;

    @Schema(description = "访问权限创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Schema(description = "访问权限更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
