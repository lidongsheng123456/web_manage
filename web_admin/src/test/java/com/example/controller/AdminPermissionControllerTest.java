package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.admin.AdminPermissionController;
import com.example.system.domain.Permission;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignPermissionDTO;
import com.example.system.service.AdminPermissionService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminPermissionController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminPermissionController 权限管理接口测试")
class AdminPermissionControllerTest {

    @Mock
    private AdminPermissionService adminPermissionService;

    private AdminPermissionController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminPermissionController(adminPermissionService);
    }

    @Test
    @DisplayName("addPermission 成功")
    void addPermission() {
        Permission p = new Permission();
        p.setPermissionName("测试权限");

        Result result = controller.addPermission(p);

        verify(adminPermissionService).addPermission(p);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("batchDeletePermission 成功")
    void batchDeletePermission() {
        List<Long> ids = List.of(2L, 3L);

        Result result = controller.batchDeletePermission(ids);

        verify(adminPermissionService).batchDeletePermission(ids);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updatePermission 成功")
    void updatePermission() {
        Permission p = new Permission();
        p.setId(2L);

        Result result = controller.updatePermission(p);

        verify(adminPermissionService).updatePermission(p);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryPermission 分页查询")
    void queryPermission() {
        Permission p = new Permission();
        PageInfo<Permission> page = new PageInfo<>(List.of());
        when(adminPermissionService.queryPermission(p, 1, 10)).thenReturn(page);

        Result<PageInfo<Permission>> result = controller.queryPermission(p, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryPermissionById 成功")
    void queryPermissionById() {
        Permission p = new Permission();
        p.setId(1L);
        p.setPermissionName("admin:user:add");
        when(adminPermissionService.queryPermissionById(1L)).thenReturn(p);

        Result<Permission> result = controller.queryPermissionById(1L);

        assertEquals(200, result.getCode());
        assertEquals("admin:user:add", result.getData().getPermissionName());
    }

    @Test
    @DisplayName("queryRoleNotPermissionId 分页查询")
    void queryRoleNotPermissionId() {
        Role role = new Role();
        PageInfo<Role> page = new PageInfo<>(List.of());
        when(adminPermissionService.queryRoleNotPermissionId(role, 1, 10)).thenReturn(page);

        Result<PageInfo<Role>> result = controller.queryRoleNotPermissionId(role, 1, 10);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryRoleByPermissionId 分页查询")
    void queryRoleByPermissionId() {
        Role role = new Role();
        PageInfo<Role> page = new PageInfo<>(List.of());
        when(adminPermissionService.queryRoleByPermissionId(role, 1, 10)).thenReturn(page);

        Result<PageInfo<Role>> result = controller.queryRoleByPermissionId(role, 1, 10);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("assignPermission 成功")
    void assignPermission() {
        AssignPermissionDTO dto = new AssignPermissionDTO();

        Result result = controller.assignPermission(dto);

        verify(adminPermissionService).assignPermission(dto);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("removePermission 成功")
    void removePermission() {
        AssignPermissionDTO dto = new AssignPermissionDTO();

        Result result = controller.removePermission(dto);

        verify(adminPermissionService).removePermission(dto);
        assertEquals(200, result.getCode());
    }
}
