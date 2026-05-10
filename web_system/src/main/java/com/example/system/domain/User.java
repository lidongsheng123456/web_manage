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
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户密码")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "用户名")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "用户手机号")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户头像地址")
    private String imgUrl;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "用户创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Schema(description = "用户更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
