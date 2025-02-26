package com.example.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "访问权限ID")
    private Long permissionId;

    @Schema(description = "角色ID")
    private List<Long> roleId;
}
