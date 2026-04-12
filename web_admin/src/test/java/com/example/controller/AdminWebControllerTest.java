package com.example.controller;

import com.example.common.config.AppConfig;
import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.redis.RedisCache;
import com.example.controller.admin.AdminWebController;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.AdminWebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminWebController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminWebController 后台 Web 接口测试")
class AdminWebControllerTest {

    @Mock
    private AdminWebService adminWebService;

    @Mock
    private RedisCache redisCache;

    private AppConfig appConfig;
    private AdminWebController controller;

    @BeforeEach
    void setUp() throws Exception {
        appConfig = new AppConfig();
        controller = new AdminWebController(adminWebService, appConfig, redisCache);
    }

    @Test
    @DisplayName("login 成功应返回 UserVo")
    void loginSuccess() {
        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("123456");
        dto.setCode("abcd");

        UserVo userVo = new UserVo();
        userVo.setUsername("admin");
        MockHttpSession session = new MockHttpSession();

        when(adminWebService.login(dto, session)).thenReturn(userVo);

        Result<UserVo> result = controller.login(dto, session);

        assertEquals(200, result.getCode());
        assertEquals("admin", result.getData().getUsername());
    }

    @Test
    @DisplayName("login 服务层抛异常应传播")
    void loginServiceException() {
        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("123456");
        dto.setCode("wrong");
        MockHttpSession session = new MockHttpSession();

        when(adminWebService.login(dto, session))
                .thenThrow(new BusinessException(ResultCodeEnum.CAPTCHA_ERROR));

        assertThrows(BusinessException.class, () -> controller.login(dto, session));
    }

    @Test
    @DisplayName("getCaptcha 应将验证码存储到 Redis")
    void getCaptchaSetsRedisCache() throws Exception {
        MockHttpSession session = new MockHttpSession();
        org.springframework.mock.web.MockHttpServletResponse response =
                new org.springframework.mock.web.MockHttpServletResponse();

        controller.getCaptcha(session, response);

        // 验证 Redis 被调用存储验证码
        verify(redisCache).setCacheObject(
                eq(appConfig.getCaptcha().getSessionKey() + ":" + session.getId()),
                anyString(), anyInt(), any());
        assertEquals("image/jpeg", response.getContentType());
    }

    @Test
    @DisplayName("updatePerson 应调用服务层")
    void updatePerson() {
        com.example.system.domain.User user = new com.example.system.domain.User();
        user.setId(1L);

        Result result = controller.updatePerson(user);

        verify(adminWebService).updatePerson(user);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("validateFormerPassword 应调用服务层")
    void validateFormerPassword() {
        Result result = controller.validateFormerPassword("oldpwd");

        verify(adminWebService).validateFormerPassword("oldpwd");
        assertEquals(200, result.getCode());
    }
}
