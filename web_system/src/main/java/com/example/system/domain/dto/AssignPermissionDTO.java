package com.example.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignPermissionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "权限ID不能为空")
    @Schema(description = "访问权限ID")
    private Long permissionId;

    @NotEmpty(message = "角色ID列表不能为空")
    @Schema(description = "角色ID")
    private List<Long> roleId;
}
