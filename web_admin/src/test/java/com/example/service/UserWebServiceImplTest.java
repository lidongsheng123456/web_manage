package com.example.service;

import cn.hutool.crypto.digest.BCrypt;
import com.example.common.config.AppConfig;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.redis.RedisCache;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.UserWebMapper;
import com.example.system.service.impl.UserWebServiceImpl;
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
 * UserWebServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserWebServiceImpl 前台登录/注册服务测试")
class UserWebServiceImplTest {

    @Mock
    private UserWebMapper userWebMapper;

    @Mock
    private RedisCache redisCache;

    private AppConfig appConfig;
    private UserWebServiceImpl userWebService;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        userWebService = new UserWebServiceImpl(userWebMapper, appConfig, redisCache);
    }

    // ========== validateCaptcha ==========

    @Test
    @DisplayName("验证码正确应返回 false")
    void validateCaptchaCorrect() {
        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("xYz1");

        assertFalse(userWebService.validateCaptcha("xyz1", session));
        verify(redisCache).deleteObject(captchaKey);
    }

    @Test
    @DisplayName("验证码错误应返回 true")
    void validateCaptchaWrong() {
        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("xYz1");

        assertTrue(userWebService.validateCaptcha("wrong", session));
    }

    @Test
    @DisplayName("验证码不存在应返回 true")
    void validateCaptchaNotExist() {
        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn(null);

        assertTrue(userWebService.validateCaptcha("xyz1", session));
    }

    // ========== login ==========

    @Test
    @DisplayName("login 参数缺失抛出异常")
    void loginMissingParams() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        MockHttpSession session = new MockHttpSession();

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.login(dto, session));
        assertEquals(ResultCodeEnum.CAPTCHA_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 验证码错误抛出异常")
    void loginCaptchaError() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        dto.setPassword("123456");
        dto.setCode("wrong");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("right");

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.login(dto, session));
        assertEquals(ResultCodeEnum.CAPTCHA_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 用户不存在抛出异常")
    void loginUserNotExist() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        dto.setPassword("123456");
        dto.setCode("abcd");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("abcd");

        when(userWebMapper.selectByUsername("user1")).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.login(dto, session));
        assertEquals(ResultCodeEnum.USER_NOT_EXIST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 密码错误抛出异常")
    void loginWrongPassword() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        dto.setPassword("wrongpwd");
        dto.setCode("abcd");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("abcd");

        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword(BCrypt.hashpw("123456"));
        when(userWebMapper.selectByUsername("user1")).thenReturn(user);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.login(dto, session));
        assertEquals(ResultCodeEnum.USER_PASSWORD_ERROR.code, ex.getCode());
    }

    // ========== register ==========

    @Test
    @DisplayName("register 参数缺失抛出异常")
    void registerMissingParams() {
        UserDto dto = new UserDto();
        dto.setUsername("newuser");
        MockHttpSession session = new MockHttpSession();

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.register(dto, session));
        assertEquals(ResultCodeEnum.CAPTCHA_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("register 验证码错误抛出异常")
    void registerCaptchaError() {
        UserDto dto = new UserDto();
        dto.setUsername("newuser");
        dto.setPassword("123456");
        dto.setCode("wrong");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("right");

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.register(dto, session));
        assertEquals(ResultCodeEnum.CAPTCHA_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("register 用户已存在抛出异常")
    void registerUserExists() {
        UserDto dto = new UserDto();
        dto.setUsername("existUser");
        dto.setPassword("123456");
        dto.setCode("abcd");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("abcd");

        when(userWebMapper.selectByUsername("existUser")).thenReturn(new User());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.register(dto, session));
        assertEquals(ResultCodeEnum.USER_EXIST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("register 成功注册")
    void registerSuccess() {
        UserDto dto = new UserDto();
        dto.setUsername("newuser");
        dto.setPassword("123456");
        dto.setCode("abcd");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("abcd");

        when(userWebMapper.selectByUsername("newuser")).thenReturn(null);
        when(userWebMapper.register(any(UserDto.class))).thenReturn(1);

        assertDoesNotThrow(() -> userWebService.register(dto, session));
        verify(userWebMapper).register(any(UserDto.class));
    }
}
