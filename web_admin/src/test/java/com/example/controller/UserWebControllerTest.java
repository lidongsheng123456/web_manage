package com.example.controller;

import com.example.common.config.AppConfig;
import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.redis.RedisCache;
import com.example.controller.user.UserWebController;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.UserWebService;
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
 * UserWebController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserWebController 前台 Web 接口测试")
class UserWebControllerTest {

    @Mock
    private UserWebService userWebService;

    @Mock
    private RedisCache redisCache;

    private AppConfig appConfig;
    private UserWebController controller;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        controller = new UserWebController(userWebService, appConfig, redisCache);
    }

    @Test
    @DisplayName("login 成功应返回 UserVo")
    void loginSuccess() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        dto.setPassword("123456");
        dto.setCode("abcd");

        UserVo userVo = new UserVo();
        userVo.setUsername("user1");
        MockHttpSession session = new MockHttpSession();

        when(userWebService.login(dto, session)).thenReturn(userVo);

        Result<UserVo> result = controller.login(dto, session);

        assertEquals(200, result.getCode());
        assertEquals("user1", result.getData().getUsername());
    }

    @Test
    @DisplayName("login 服务层抛异常应传播")
    void loginServiceException() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        dto.setPassword("123456");
        dto.setCode("wrong");
        MockHttpSession session = new MockHttpSession();

        when(userWebService.login(dto, session))
                .thenThrow(new BusinessException(ResultCodeEnum.CAPTCHA_ERROR));

        assertThrows(BusinessException.class, () -> controller.login(dto, session));
    }

    @Test
    @DisplayName("register 成功")
    void registerSuccess() {
        UserDto dto = new UserDto();
        dto.setUsername("newuser");
        dto.setPassword("123456");
        dto.setCode("abcd");
        MockHttpSession session = new MockHttpSession();

        Result result = controller.register(dto, session);

        verify(userWebService).register(dto, session);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("getCaptcha 应将验证码存储到 Redis")
    void getCaptchaSetsRedisCache() throws Exception {
        MockHttpSession session = new MockHttpSession();
        org.springframework.mock.web.MockHttpServletResponse response =
                new org.springframework.mock.web.MockHttpServletResponse();

        controller.getCaptcha(session, response);

        verify(redisCache).setCacheObject(
                eq(appConfig.getCaptcha().getSessionKey() + ":" + session.getId()),
                anyString(), anyInt(), any());
        assertEquals("image/jpeg", response.getContentType());
    }

    @Test
    @DisplayName("updatePerson 应调用服务层")
    void updatePerson() {
        User user = new User();
        user.setId(1L);

        Result result = controller.updatePerson(user);

        verify(userWebService).updatePerson(user);
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("validateFormerPassword 应调用服务层")
    void validateFormerPassword() {
        Result result = controller.validateFormerPassword("oldpwd");

        verify(userWebService).validateFormerPassword("oldpwd");
        assertEquals(200, result.getCode());
    }

    @Test
    @DisplayName("queryCurrentUser 应返回用户信息")
    void queryCurrentUser() {
        UserVo userVo = new UserVo();
        userVo.setUsername("user1");
        when(userWebService.queryCurrentUser()).thenReturn(userVo);

        Result<UserVo> result = controller.queryCurrentUser();

        assertEquals(200, result.getCode());
        assertEquals("user1", result.getData().getUsername());
    }
}
