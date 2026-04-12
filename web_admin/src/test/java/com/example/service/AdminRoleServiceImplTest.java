package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignRoleDTO;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminRoleAndPermissionMapper;
import com.example.system.mapper.AdminRoleMapper;
import com.example.system.mapper.AdminUserAndRoleMapper;
import com.example.system.service.impl.AdminRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminRoleServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminRoleServiceImpl 角色管理服务测试")
class AdminRoleServiceImplTest {

    @Mock
    private AdminRoleMapper adminRoleMapper;

    @Mock
    private AdminUserAndRoleMapper adminUserAndRoleMapper;

    @Mock
    private AdminRoleAndPermissionMapper adminRoleAndPermissionMapper;

    @Mock
    private AdminRbacMapper adminRbacMapper;

    private AdminRoleServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminRoleServiceImpl(adminRoleMapper, adminUserAndRoleMapper, adminRoleAndPermissionMapper, adminRbacMapper);
    }

    @Test
    @DisplayName("addRole 成功")
    void addRoleSuccess() {
        Role role = new Role();
        when(adminRoleMapper.addRole(role)).thenReturn(1);

        assertDoesNotThrow(() -> service.addRole(role));
        verify(adminRoleMapper).addRole(role);
    }

    @Test
    @DisplayName("batchDeleteRole ids 为空应抛出异常")
    void batchDeleteRoleEmptyIds() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.batchDeleteRole(List.of()));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteRole 包含超级管理员角色 id=1 应抛出异常")
    void batchDeleteRoleSuperAdmin() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.batchDeleteRole(List.of(1L, 2L)));
        assertEquals(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteRole 正常删除")
    void batchDeleteRoleSuccess() {
        List<Long> ids = List.of(2L, 3L);
        when(adminRoleMapper.batchDeleteRole(ids)).thenReturn(2);

        assertDoesNotThrow(() -> service.batchDeleteRole(ids));
        verify(adminUserAndRoleMapper).batchDeleteUserAndRoleByRoleId(ids);
        verify(adminRoleAndPermissionMapper).batchDeleteRoleAndPermissionByRoleId(ids);
        verify(adminRoleMapper).batchDeleteRole(ids);
    }

    @Test
    @DisplayName("updateRole 修改超级管理员角色应抛出异常")
    void updateRoleSuperAdmin() {
        Role role = new Role();
        role.setId(1L);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.updateRole(role));
        assertEquals(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("updateRole 正常修改")
    void updateRoleSuccess() {
        Role role = new Role();
        role.setId(2L);
        when(adminRoleMapper.updateRole(role)).thenReturn(1);

        assertDoesNotThrow(() -> service.updateRole(role));
    }

    @Test
    @DisplayName("queryRoleById id 为空应抛出异常")
    void queryRoleByIdNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.queryRoleById(null));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("queryRoleById 正常查询")
    void queryRoleByIdSuccess() {
        Role role = new Role();
        role.setId(2L);
        role.setRoleName("editor");
        when(adminRoleMapper.queryRoleById(2L)).thenReturn(role);

        Role result = service.queryRoleById(2L);

        assertNotNull(result);
        assertEquals("editor", result.getRoleName());
    }

    @Test
    @DisplayName("queryAllRole 返回列表")
    void queryAllRole() {
        when(adminRoleMapper.queryAllRole()).thenReturn(List.of(new Role()));

        List<Role> result = service.queryAllRole();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("assignRole 用户已分配当前角色应抛出异常")
    void assignRoleAlreadyAssigned() {
        AssignRoleDTO dto = new AssignRoleDTO();
        dto.setUserId(List.of(2L));
        dto.setRoleId(10L);

        when(adminUserAndRoleMapper.validateIsExistByUserId(2L, 10L)).thenReturn(1);

        assertThrows(BusinessException.class, () -> service.assignRole(dto));
    }

    @Test
    @DisplayName("assignRole 成功分配")
    void assignRoleSuccess() {
        AssignRoleDTO dto = new AssignRoleDTO();
        dto.setUserId(List.of(2L));
        dto.setRoleId(10L);

        when(adminUserAndRoleMapper.validateIsExistByUserId(2L, 10L)).thenReturn(0);
        when(adminUserAndRoleMapper.assignRole(List.of(2L), 10L)).thenReturn(1);

        assertDoesNotThrow(() -> service.assignRole(dto));
    }

    @Test
    @DisplayName("removeRole 包含超级管理员应抛出异常")
    void removeRoleSuperAdmin() {
        AssignRoleDTO dto = new AssignRoleDTO();
        dto.setUserId(List.of(1L));
        dto.setRoleId(10L);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.removeRole(dto));
        assertEquals(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("removeRole 用户只剩一个角色应抛出异常")
    void removeRoleLastRole() {
        AssignRoleDTO dto = new AssignRoleDTO();
        dto.setUserId(List.of(2L));
        dto.setRoleId(10L);

        when(adminUserAndRoleMapper.getUserAndRoleByUserId(2L))
                .thenReturn(List.of(Map.of(2L, 10L)));

        assertThrows(BusinessException.class, () -> service.removeRole(dto));
    }

    @Test
    @DisplayName("removeRole 成功移除")
    void removeRoleSuccess() {
        AssignRoleDTO dto = new AssignRoleDTO();
        dto.setUserId(List.of(2L));
        dto.setRoleId(10L);

        when(adminUserAndRoleMapper.getUserAndRoleByUserId(2L))
                .thenReturn(List.of(Map.of(2L, 10L), Map.of(2L, 20L)));
        when(adminUserAndRoleMapper.removeRole(List.of(2L), 10L)).thenReturn(1);

        assertDoesNotThrow(() -> service.removeRole(dto));
    }
}
