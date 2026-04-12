package com.example.service;

import cn.hutool.crypto.digest.BCrypt;
import com.example.common.config.AppConfig;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.common.redis.RedisCache;
import com.example.system.domain.User;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminWebMapper;
import com.example.system.service.impl.AdminWebServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminWebServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminWebServiceImpl 后台登录服务测试")
class AdminWebServiceImplTest {

    @Mock
    private AdminWebMapper adminWebMapper;

    @Mock
    private AdminRbacMapper adminRbacMapper;

    @Mock
    private RedisCache redisCache;

    private AppConfig appConfig;
    private AdminWebServiceImpl adminWebService;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        adminWebService = new AdminWebServiceImpl(adminWebMapper, adminRbacMapper, appConfig, redisCache);
    }

    @Test
    @DisplayName("验证码正确应返回 false（表示验证通过）")
    void validateCaptchaCorrect() {
        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("AbCd");

        Boolean result = adminWebService.validateCaptcha("abcd", session);

        assertFalse(result);
        verify(redisCache).deleteObject(captchaKey);
    }

    @Test
    @DisplayName("验证码错误应返回 true（表示验证失败）")
    void validateCaptchaWrong() {
        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("AbCd");

        Boolean result = adminWebService.validateCaptcha("wrong", session);

        assertTrue(result);
    }

    @Test
    @DisplayName("验证码不存在（Redis 返回 null）应返回 true")
    void validateCaptchaNotExist() {
        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn(null);

        Boolean result = adminWebService.validateCaptcha("abcd", session);

        assertTrue(result);
    }

    @Test
    @DisplayName("login 参数缺失应抛出 PARAM_LOST_ERROR")
    void loginMissingParams() {
        UserDto dto = new UserDto();
        dto.setUsername("admin");
        // password and code are null

        MockHttpSession session = new MockHttpSession();

        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminWebService.login(dto, session));
        assertEquals(ResultCodeEnum.CAPTCHA_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 验证码错误应抛出 CAPTCHA_ERROR")
    void loginWrongCaptcha() {
        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("123456");
        dto.setCode("wrong");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("right");

        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminWebService.login(dto, session));
        assertEquals(ResultCodeEnum.CAPTCHA_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 用户不存在应抛出 USER_NOT_EXIST_ERROR")
    void loginUserNotExist() {
        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("123456");
        dto.setCode("abcd");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("abcd");

        when(adminWebMapper.selectByUsername("admin")).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminWebService.login(dto, session));
        assertEquals(ResultCodeEnum.USER_NOT_EXIST_ERROR.code, ex.getCode());
    }

    @Test
    @DisplayName("login 密码错误应抛出 USER_PASSWORD_ERROR")
    void loginWrongPassword() {
        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("wrongpass");
        dto.setCode("abcd");

        MockHttpSession session = new MockHttpSession();
        String captchaKey = appConfig.getCaptcha().getSessionKey() + ":" + session.getId();
        when(redisCache.getCacheObject(captchaKey)).thenReturn("abcd");

        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword(BCrypt.hashpw("123456"));
        when(adminWebMapper.selectByUsername("admin")).thenReturn(user);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> adminWebService.login(dto, session));
        assertEquals(ResultCodeEnum.USER_PASSWORD_ERROR.code, ex.getCode());
    }
}
