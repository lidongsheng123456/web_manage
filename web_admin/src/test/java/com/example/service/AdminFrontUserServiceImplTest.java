package com.example.service;

import com.example.system.domain.FrontUser;
import com.example.system.domain.vo.FrontUserVo;
import com.example.system.mapper.AdminFrontUserMapper;
import com.example.system.service.impl.AdminFrontUserServiceImpl;
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
 * AdminFrontUserServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminFrontUserServiceImpl 前台用户管理服务测试")
class AdminFrontUserServiceImplTest {

    @Mock
    private AdminFrontUserMapper adminFrontUserMapper;

    private AdminFrontUserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminFrontUserServiceImpl(adminFrontUserMapper);
    }

    @Test
    @DisplayName("queryFrontUser 模糊查询用户名")
    void queryFrontUserByUsername() {
        FrontUser frontUser = new FrontUser();
        frontUser.setUsername("test");

        FrontUser user = new FrontUser();
        user.setId(1L);
        user.setUsername("testuser");
        when(adminFrontUserMapper.selectList(any())).thenReturn(List.of(user));

        PageInfo<FrontUserVo> result = service.queryFrontUser(frontUser, 1, 10);

        assertNotNull(result);
        assertEquals(1, result.getList().size());
        assertEquals("testuser", result.getList().get(0).getUsername());
    }

    @Test
    @DisplayName("queryFrontUser 无条件查询")
    void queryFrontUserNoCondition() {
        FrontUser frontUser = new FrontUser();

        when(adminFrontUserMapper.selectList(any())).thenReturn(List.of());

        PageInfo<FrontUserVo> result = service.queryFrontUser(frontUser, 1, 10);

        assertNotNull(result);
        assertTrue(result.getList().isEmpty());
    }
}
