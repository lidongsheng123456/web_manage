package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.admin.AdminFrontUserController;
import com.example.system.domain.FrontUser;
import com.example.system.domain.vo.FrontUserVo;
import com.example.system.service.AdminFrontUserService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminFrontUserController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminFrontUserController 前台用户管理接口测试")
class AdminFrontUserControllerTest {

    @Mock
    private AdminFrontUserService adminFrontUserService;

    private AdminFrontUserController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminFrontUserController(adminFrontUserService);
    }

    @Test
    @DisplayName("addFrontUser 成功")
    void addFrontUserSuccess() {
        FrontUser frontUser = new FrontUser();
        when(adminFrontUserService.save(frontUser)).thenReturn(true);

        Result result = controller.addFrontUser(frontUser);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("addFrontUser 失败返回500")
    void addFrontUserFail() {
        FrontUser frontUser = new FrontUser();
        when(adminFrontUserService.save(frontUser)).thenReturn(false);

        Result result = controller.addFrontUser(frontUser);

        assertEquals(500, result.getCode());
    }

    @Test
    @DisplayName("batchDeleteFrontUser 成功")
    void batchDeleteFrontUserSuccess() {
        List<Long> ids = List.of(1L, 2L);
        when(adminFrontUserService.removeBatchByIds(ids)).thenReturn(true);

        Result result = controller.batchDeleteFrontUser(ids);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("batchDeleteFrontUser 失败返回500")
    void batchDeleteFrontUserFail() {
        List<Long> ids = List.of(1L);
        when(adminFrontUserService.removeBatchByIds(ids)).thenReturn(false);

        Result result = controller.batchDeleteFrontUser(ids);

        assertEquals(500, result.getCode());
    }

    @Test
    @DisplayName("updateFrontUser 成功")
    void updateFrontUserSuccess() {
        FrontUser frontUser = new FrontUser();
        when(adminFrontUserService.updateById(frontUser)).thenReturn(true);

        Result result = controller.updateFrontUser(frontUser);

        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("updateFrontUser 失败返回500")
    void updateFrontUserFail() {
        FrontUser frontUser = new FrontUser();
        when(adminFrontUserService.updateById(frontUser)).thenReturn(false);

        Result result = controller.updateFrontUser(frontUser);

        assertEquals(500, result.getCode());
    }

    @Test
    @DisplayName("queryFrontUser 分页查询")
    void queryFrontUser() {
        FrontUser frontUser = new FrontUser();
        PageInfo<FrontUserVo> page = new PageInfo<>(List.of());
        when(adminFrontUserService.queryFrontUser(frontUser, 1, 10)).thenReturn(page);

        Result<PageInfo<FrontUserVo>> result = controller.queryFrontUser(frontUser, 1, 10);

        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("queryFrontUserById 成功")
    void queryFrontUserById() {
        FrontUser frontUser = new FrontUser();
        frontUser.setId(1L);
        frontUser.setUsername("testuser");
        when(adminFrontUserService.getById(1L)).thenReturn(frontUser);

        Result<FrontUserVo> result = controller.queryFrontUserById(1L);

        assertEquals(200, result.getCode());
        assertEquals("testuser", result.getData().getUsername());
    }

    @Test
    @DisplayName("exportExcel 导出 Excel")
    void exportExcel() throws Exception {
        when(adminFrontUserService.list()).thenReturn(List.of());

        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.exportExcel(response);

        verify(adminFrontUserService).list();
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                response.getContentType());
    }
}
