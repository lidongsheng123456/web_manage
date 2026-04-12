package com.example.controller;

import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.controller.admin.AdminUserController;
import com.example.system.domain.User;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.AdminUserService;
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
 * AdminUserController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminUserController 用户管理接口测试")
class AdminUserControllerTest {

    @Mock
    private AdminUserService adminUserService;

    private AdminUserController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminUserController(adminUserService);
    }

    @Test
    @DisplayName("addUser 成功")
    void addUserSuccess() {
        User user = new User();
        user.setUsername("newuser");

        Result result = controller.addUser(user);

        verify(adminUserService).addUser(user);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("addUser 服务层抛异常应传播")
    void addUserException() {
        User user = new User();
        doThrow(new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR))
                .when(adminUserService).addUser(user);

        assertThrows(BusinessException.class, () -> controller.addUser(user));
    }

    @Test
    @DisplayName("batchDeleteUser 成功")
    void batchDeleteUserSuccess() {
        List<Long> ids = List.of(2L, 3L);

        Result result = controller.batchDeleteUser(ids);

        verify(adminUserService).batchDeleteUser(ids);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updateUser 成功")
    void updateUserSuccess() {
        User user = new User();
        user.setId(2L);

        Result result = controller.updateUser(user);

        verify(adminUserService).updateUser(user);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryUser 分页查询")
    void queryUser() {
        UserVo vo = new UserVo();
        PageInfo<UserVo> page = new PageInfo<>(List.of());
        when(adminUserService.queryUser(vo, 1, 10)).thenReturn(page);

        Result<PageInfo<UserVo>> result = controller.queryUser(vo, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryUserById 成功")
    void queryUserById() {
        UserVo vo = new UserVo();
        vo.setId(2L);
        vo.setUsername("testuser");
        when(adminUserService.queryUserById(2L)).thenReturn(vo);

        Result<UserVo> result = controller.queryUserById(2L);

        assertEquals(200, result.getCode());
        assertEquals("testuser", result.getData().getUsername());
    }
}
