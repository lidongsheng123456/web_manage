package com.example.service;

import com.example.common.config.AppConfig;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
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
import org.springframework.util.DigestUtils;

import java.util.Date;

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

    private AppConfig appConfig;
    private UserWebServiceImpl userWebService;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        userWebService = new UserWebServiceImpl(userWebMapper, appConfig);
    }

    // ========== validateCaptcha ==========

    @Test
    @DisplayName("验证码正确且未过期")
    void validateCaptchaCorrect() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("captcha", "xYz1");
        session.setAttribute("date", new Date());

        assertFalse(userWebService.validateCaptcha("xyz1", session));
    }

    @Test
    @DisplayName("验证码正确但已过期")
    void validateCaptchaExpired() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("captcha", "xYz1");
        session.setAttribute("date", new Date(System.currentTimeMillis() - 120_000L));

        assertTrue(userWebService.validateCaptcha("xyz1", session));
    }

    @Test
    @DisplayName("验证码错误")
    void validateCaptchaWrong() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("captcha", "xYz1");
        session.setAttribute("date", new Date());

        assertTrue(userWebService.validateCaptcha("wrong", session));
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
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 验证码错误抛出异常")
    void loginCaptchaError() {
        UserDto dto = new UserDto();
        dto.setUsername("user1");
        dto.setPassword("123456");
        dto.setCode("wrong");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("captcha", "right");
        session.setAttribute("date", new Date());

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
        session.setAttribute("captcha", "abcd");
        session.setAttribute("date", new Date());

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
        session.setAttribute("captcha", "abcd");
        session.setAttribute("date", new Date());

        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        when(userWebMapper.selectByUsername("user1")).thenReturn(user);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userWebService.login(dto, session));
        assertEquals(ResultCodeEnum.USER_User_ERROR.code, ex.getCode());
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
        assertEquals(ResultCodeEnum.PARAM_LOST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("register 验证码错误抛出异常")
    void registerCaptchaError() {
        UserDto dto = new UserDto();
        dto.setUsername("newuser");
        dto.setPassword("123456");
        dto.setCode("wrong");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("captcha", "right");
        session.setAttribute("date", new Date());

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
        session.setAttribute("captcha", "abcd");
        session.setAttribute("date", new Date());

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
        session.setAttribute("captcha", "abcd");
        session.setAttribute("date", new Date());

        when(userWebMapper.selectByUsername("newuser")).thenReturn(null);
        when(userWebMapper.register(any(UserDto.class))).thenReturn(1);

        assertDoesNotThrow(() -> userWebService.register(dto, session));
        verify(userWebMapper).register(any(UserDto.class));
    }
}
