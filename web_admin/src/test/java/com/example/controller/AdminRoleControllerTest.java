package com.example.controller;

import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.controller.admin.AdminRoleController;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignRoleDTO;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.AdminRoleService;
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
 * AdminRoleController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminRoleController 角色管理接口测试")
class AdminRoleControllerTest {

    @Mock
    private AdminRoleService adminRoleService;

    private AdminRoleController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminRoleController(adminRoleService);
    }

    @Test
    @DisplayName("addRole 成功")
    void addRoleSuccess() {
        Role role = new Role();
        role.setRoleName("测试角色");

        Result result = controller.addRole(role);

        verify(adminRoleService).addRole(role);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("batchDeleteRole 成功")
    void batchDeleteRoleSuccess() {
        List<Long> ids = List.of(2L, 3L);

        Result result = controller.batchDeleteRole(ids);

        verify(adminRoleService).batchDeleteRole(ids);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updateRole 成功")
    void updateRoleSuccess() {
        Role role = new Role();
        role.setId(2L);

        Result result = controller.updateRole(role);

        verify(adminRoleService).updateRole(role);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryRole 分页查询")
    void queryRole() {
        Role role = new Role();
        PageInfo<Role> page = new PageInfo<>(List.of());
        when(adminRoleService.queryRole(role, 1, 10)).thenReturn(page);

        Result<PageInfo<Role>> result = controller.queryRole(role, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryRoleById 成功")
    void queryRoleById() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("admin");
        when(adminRoleService.queryRoleById(1L)).thenReturn(role);

        Result<Role> result = controller.queryRoleById(1L);

        assertEquals(200, result.getCode());
        assertEquals("admin", result.getData().getRoleName());
    }

    @Test
    @DisplayName("queryUserNotRoleId 分页查询")
    void queryUserNotRoleId() {
        UserVo vo = new UserVo();
        PageInfo<UserVo> page = new PageInfo<>(List.of());
        when(adminRoleService.queryUserNotRoleId(vo, 1, 10)).thenReturn(page);

        Result<PageInfo<UserVo>> result = controller.queryUserNotRoleId(vo, 1, 10);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryUserByRoleId 分页查询")
    void queryUserByRoleId() {
        UserVo vo = new UserVo();
        PageInfo<UserVo> page = new PageInfo<>(List.of());
        when(adminRoleService.queryUserByRoleId(vo, 1, 10)).thenReturn(page);

        Result<PageInfo<UserVo>> result = controller.queryUserByRoleId(vo, 1, 10);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("assignRole 成功")
    void assignRole() {
        AssignRoleDTO dto = new AssignRoleDTO();

        Result result = controller.assignRole(dto);

        verify(adminRoleService).assignRole(dto);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("removeRole 成功")
    void removeRole() {
        AssignRoleDTO dto = new AssignRoleDTO();

        Result result = controller.removeRole(dto);

        verify(adminRoleService).removeRole(dto);
        assertEquals(200, result.getCode());
    }
}
