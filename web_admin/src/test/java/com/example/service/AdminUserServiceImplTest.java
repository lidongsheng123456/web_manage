package com.example.service;

import com.example.common.config.AppConfig;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.User;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.AdminNoticeMapper;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminUserAndRoleMapper;
import com.example.system.mapper.AdminUserMapper;
import com.example.system.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cn.hutool.crypto.digest.BCrypt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminUserServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminUserServiceImpl 用户管理服务测试")
class AdminUserServiceImplTest {

    @Mock
    private AdminUserMapper adminUserMapper;

    @Mock
    private AdminNoticeMapper adminNoticeMapper;

    @Mock
    private AdminUserAndRoleMapper adminUserAndRoleMapper;

    @Mock
    private AdminRbacMapper adminRbacMapper;

    private AppConfig appConfig;
    private AdminUserServiceImpl adminUserService;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        adminUserService = new AdminUserServiceImpl(adminUserMapper, adminNoticeMapper, adminUserAndRoleMapper, adminRbacMapper, appConfig);
    }

    // ========== addUser ==========

    @Test
    @DisplayName("addUser 应使用默认密码加密")
    void addUserUsesDefaultPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setPhone("13800000000");
        user.setEmail("test@test.com");
        user.setId(10L);

        when(adminUserMapper.addUser(any(User.class))).thenReturn(1);
        when(adminUserAndRoleMapper.addUserAndRoleId(anyLong(), anyLong())).thenReturn(1);

        adminUserService.addUser(user);

        assertTrue(BCrypt.checkpw("123456", user.getPassword()));
        verify(adminUserMapper).addUser(user);
    }

    @Test
    @DisplayName("addUser 使用自定义默认密码")
    void addUserUsesCustomDefaultPassword() {
        appConfig.setDefaultPassword("abcdef");

        User user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setPhone("13800000000");
        user.setEmail("test@test.com");
        user.setId(10L);

        when(adminUserMapper.addUser(any(User.class))).thenReturn(1);
        when(adminUserAndRoleMapper.addUserAndRoleId(anyLong(), anyLong())).thenReturn(1);

        adminUserService.addUser(user);

        assertTrue(BCrypt.checkpw("abcdef", user.getPassword()));
    }

    @Test
    @DisplayName("addUser 字段缺失应抛出异常")
    void addUserMissingFields() {
        User user = new User();
        user.setUsername("test");
        // name, phone, email are null

        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminUserService.addUser(user));
        assertEquals(ResultCodeEnum.SYSTEM_ERROR.code, ex.getCode());
    }

    // ========== batchDeleteUser ==========

    @Test
    @DisplayName("batchDeleteUser ids 为空应抛出异常")
    void batchDeleteUserEmptyIds() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminUserService.batchDeleteUser(List.of()));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteUser 包含超级管理员 id=1 应抛出异常")
    void batchDeleteUserSuperAdmin() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminUserService.batchDeleteUser(List.of(1L, 2L)));
        assertEquals(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("batchDeleteUser 正常删除")
    void batchDeleteUserSuccess() {
        List<Long> ids = List.of(2L, 3L);
        when(adminUserMapper.batchDeleteUser(ids)).thenReturn(2);

        assertDoesNotThrow(() -> adminUserService.batchDeleteUser(ids));
        verify(adminNoticeMapper).batchDeleteNoticeByUserIds(ids);
        verify(adminUserAndRoleMapper).batchDeleteUserAndRoleByUserId(ids);
        verify(adminUserMapper).batchDeleteUser(ids);
    }

    // ========== updateUser ==========

    @Test
    @DisplayName("updateUser 修改超级管理员应抛出异常")
    void updateUserSuperAdmin() {
        User user = new User();
        user.setId(1L);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminUserService.updateUser(user));
        assertEquals(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("updateUser 正常修改")
    void updateUserSuccess() {
        User user = new User();
        user.setId(2L);
        user.setUsername("updated");
        user.setName("Updated");
        user.setPhone("13800000001");
        user.setEmail("up@test.com");

        when(adminUserMapper.updateUser(user)).thenReturn(1);

        assertDoesNotThrow(() -> adminUserService.updateUser(user));
    }

    // ========== queryUserById ==========

    @Test
    @DisplayName("queryUserById id 为空应抛出异常")
    void queryUserByIdNull() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminUserService.queryUserById(null));
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("queryUserById 正常查询")
    void queryUserByIdSuccess() {
        User user = new User();
        user.setId(2L);
        user.setUsername("testuser");
        when(adminUserMapper.queryUserById(2L)).thenReturn(user);
        when(adminRbacMapper.getPermissionList(2L)).thenReturn(List.of());
        when(adminRbacMapper.getRoleList(2L)).thenReturn(List.of());

        UserVo result = adminUserService.queryUserById(2L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
}
