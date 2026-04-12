package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Permission;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignPermissionDTO;
import com.example.system.mapper.AdminPermissionMapper;
import com.example.system.mapper.AdminRoleAndPermissionMapper;
import com.example.system.service.impl.AdminPermissionServiceImpl;
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
 * AdminPermissionServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminPermissionServiceImpl 权限管理服务测试")
class AdminPermissionServiceImplTest {

    @Mock
    private AdminPermissionMapper adminPermissionMapper;

    @Mock
    private AdminRoleAndPermissionMapper adminRoleAndPermissionMapper;

    private AdminPermissionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminPermissionServiceImpl(adminPermissionMapper, adminRoleAndPermissionMapper);
    }

    @Test
    @DisplayName("addPermission 成功")
    void addPermissionSuccess() {
        Permission permission = new Permission();
        when(adminPermissionMapper.addPermission(permission)).thenReturn(1);

        assertDoesNotThrow(() -> service.addPermission(permission));
        verify(adminPermissionMapper).addPermission(permission);
    }

    @Test
    @DisplayName("batchDeletePermission ids 为空应抛出异常")
    void batchDeletePermissionEmptyIds() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.batchDeletePermission(List.of()));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeletePermission 正常删除")
    void batchDeletePermissionSuccess() {
        List<Long> ids = List.of(1L, 2L);
        when(adminPermissionMapper.batchDeletePermission(ids)).thenReturn(2);

        assertDoesNotThrow(() -> service.batchDeletePermission(ids));
        verify(adminRoleAndPermissionMapper).batchDeleteRoleAndPermissionByRoleId(ids);
        verify(adminPermissionMapper).batchDeletePermission(ids);
    }

    @Test
    @DisplayName("updatePermission 成功")
    void updatePermissionSuccess() {
        Permission permission = new Permission();
        when(adminPermissionMapper.updatePermission(permission)).thenReturn(1);

        assertDoesNotThrow(() -> service.updatePermission(permission));
    }

    @Test
    @DisplayName("queryPermissionById id 为空应抛出异常")
    void queryPermissionByIdNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.queryPermissionById(null));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("queryPermissionById 正常查询")
    void queryPermissionByIdSuccess() {
        Permission permission = new Permission();
        permission.setId(1L);
        when(adminPermissionMapper.queryPermissionById(1L)).thenReturn(permission);

        Permission result = service.queryPermissionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("queryAllPermission 返回列表")
    void queryAllPermission() {
        when(adminPermissionMapper.queryAllPermission()).thenReturn(List.of(new Permission()));

        List<Permission> result = service.queryAllPermission();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("assignPermission 角色已分配应抛出异常")
    void assignPermissionAlreadyAssigned() {
        AssignPermissionDTO dto = new AssignPermissionDTO();
        dto.setRoleId(List.of(1L));
        dto.setPermissionId(10L);

        when(adminRoleAndPermissionMapper.validateIsExistByRoleId(1L, 10L)).thenReturn(1);

        assertThrows(BusinessException.class, () -> service.assignPermission(dto));
    }

    @Test
    @DisplayName("assignPermission 成功分配")
    void assignPermissionSuccess() {
        AssignPermissionDTO dto = new AssignPermissionDTO();
        dto.setRoleId(List.of(2L));
        dto.setPermissionId(10L);

        when(adminRoleAndPermissionMapper.validateIsExistByRoleId(2L, 10L)).thenReturn(0);
        when(adminRoleAndPermissionMapper.assignPermission(List.of(2L), 10L)).thenReturn(1);

        assertDoesNotThrow(() -> service.assignPermission(dto));
    }

    @Test
    @DisplayName("removePermission 成功移除")
    void removePermissionSuccess() {
        AssignPermissionDTO dto = new AssignPermissionDTO();
        dto.setRoleId(List.of(2L));
        dto.setPermissionId(10L);

        when(adminRoleAndPermissionMapper.removePermission(List.of(2L), 10L)).thenReturn(1);

        assertDoesNotThrow(() -> service.removePermission(dto));
    }

    @Test
    @DisplayName("queryRoleNotPermissionId 分页查询")
    void queryRoleNotPermissionId() {
        Role role = new Role();
        when(adminPermissionMapper.queryRoleNotPermissionId(role)).thenReturn(List.of());

        PageInfo<Role> result = service.queryRoleNotPermissionId(role, 1, 10);

        assertNotNull(result);
    }

    @Test
    @DisplayName("queryRoleByPermissionId 分页查询")
    void queryRoleByPermissionId() {
        Role role = new Role();
        when(adminPermissionMapper.queryRoleByPermissionId(role)).thenReturn(List.of());

        PageInfo<Role> result = service.queryRoleByPermissionId(role, 1, 10);

        assertNotNull(result);
    }
}
