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

    @Schema(description = "后台用户每天总数列表")
    private List<DataCountTypeVo> user;

    @Schema(description = "通知每天总数列表")
    private List<DataCountTypeVo> notice;

    @Schema(description = "操作日志每天总数列表")
    private List<DataCountTypeVo> operLog;

    @Schema(description = "访问权限每天总数列表")
    private List<DataCountTypeVo> permission;

    @Schema(description = "前台用户每天总数列表")
    private List<DataCountTypeVo> frontUser;

    @Schema(description = "角色每天总数列表")
    private List<DataCountTypeVo> role;

    @Schema(description = "字典数据每天总数列表")
    private List<DataCountTypeVo> dictData;

    @Schema(description = "通用查询每天总数列表")
    private List<DataCountTypeVo> comQuery;

    @Schema(description = "角色权限关联每天总数列表")
    private List<DataCountTypeVo> rolePermission;

    @Schema(description = "用户角色关联每天总数列表")
    private List<DataCountTypeVo> userRole;

    @Schema(description = "后台用户总数")
    private Long totalUser;

    @Schema(description = "通知总数")
    private Long totalNotice;

    @Schema(description = "操作日志总数")
    private Long totalOperLog;

    @Schema(description = "访问权限总数")
    private Long totalPermission;

    @Schema(description = "前台用户总数")
    private Long totalFrontUser;

    @Schema(description = "角色总数")
    private Long totalRole;

    @Schema(description = "字典数据总数")
    private Long totalDictData;

    @Schema(description = "通用查询总数")
    private Long totalComQuery;

    @Schema(description = "角色权限关联总数")
    private Long totalRolePermission;

    @Schema(description = "用户角色关联总数")
    private Long totalUserRole;
}
