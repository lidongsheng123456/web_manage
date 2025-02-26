package com.example.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户名")
    private String name;

    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户头像地址")
    private String imgUrl;

    @Schema(description = "用户创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Schema(description = "用户更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @Schema(description = "用户所有访问权限列表")
    private List<Map<String, Object>> permissions = new ArrayList<>();

    @Schema(description = "用户所有角色列表")
    private List<Map<String, Object>> roles = new ArrayList<>();
}
