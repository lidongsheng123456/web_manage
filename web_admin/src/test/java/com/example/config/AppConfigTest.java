package com.example.config;

import com.example.common.config.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AppConfig 配置类单元测试
 */
@DisplayName("AppConfig 配置绑定测试")
class AppConfigTest {

    @Test
    @DisplayName("默认值应正确初始化")
    void defaultValuesShouldBeCorrect() {
        AppConfig config = new AppConfig();

        // captcha defaults
        assertEquals(100, config.getCaptcha().getWidth());
        assertEquals(40, config.getCaptcha().getHeight());
        assertEquals(60_000L, config.getCaptcha().getExpiration());
        assertEquals("captcha", config.getCaptcha().getSessionKey());
        assertEquals("date", config.getCaptcha().getSessionDateKey());

        // default password
        assertEquals("123456", config.getDefaultPassword());

        // cache defaults
        assertEquals("notice:", config.getCache().getNoticePrefix());
        assertEquals("sa:perm:", config.getCache().getPermPrefix());
        assertEquals("sa:role:", config.getCache().getRolePrefix());
        assertEquals(30, config.getCache().getPermTtlMinutes());

        // file defaults
        assertEquals("./files/", config.getFile().getUploadPath());
        assertNotNull(config.getFile().getAllowedExtensions());
        assertTrue(config.getFile().getAllowedExtensions().contains("jpg"));
        assertTrue(config.getFile().getAllowedExtensions().contains("pdf"));
        assertTrue(config.getFile().getAllowedExtensions().contains("xlsx"));
    }

    @Test
    @DisplayName("Captcha 属性可自定义设置")
    void captchaPropertiesCanBeCustomized() {
        AppConfig.Captcha captcha = new AppConfig.Captcha();
        captcha.setWidth(200);
        captcha.setHeight(80);
        captcha.setExpiration(120_000L);
        captcha.setSessionKey("myKey");
        captcha.setSessionDateKey("myDate");

        assertEquals(200, captcha.getWidth());
        assertEquals(80, captcha.getHeight());
        assertEquals(120_000L, captcha.getExpiration());
        assertEquals("myKey", captcha.getSessionKey());
        assertEquals("myDate", captcha.getSessionDateKey());
    }

    @Test
    @DisplayName("Cache 属性可自定义设置")
    void cachePropertiesCanBeCustomized() {
        AppConfig.Cache cache = new AppConfig.Cache();
        cache.setNoticePrefix("n:");
        cache.setPermPrefix("p:");
        cache.setRolePrefix("r:");
        cache.setPermTtlMinutes(60);

        assertEquals("n:", cache.getNoticePrefix());
        assertEquals("p:", cache.getPermPrefix());
        assertEquals("r:", cache.getRolePrefix());
        assertEquals(60, cache.getPermTtlMinutes());
    }

    @Test
    @DisplayName("FileConfig 属性可自定义设置")
    void fileConfigPropertiesCanBeCustomized() {
        AppConfig.FileConfig fileConfig = new AppConfig.FileConfig();
        fileConfig.setUploadPath("/custom/path/");
        fileConfig.setAllowedExtensions(Set.of("png", "mp4"));

        assertEquals("/custom/path/", fileConfig.getUploadPath());
        assertEquals(2, fileConfig.getAllowedExtensions().size());
        assertTrue(fileConfig.getAllowedExtensions().contains("png"));
        assertTrue(fileConfig.getAllowedExtensions().contains("mp4"));
        assertFalse(fileConfig.getAllowedExtensions().contains("jpg"));
    }

    @Test
    @DisplayName("AppConfig 整体可替换子对象")
    void appConfigCanReplaceNestedObjects() {
        AppConfig config = new AppConfig();
        AppConfig.Captcha captcha = new AppConfig.Captcha();
        captcha.setWidth(300);
        config.setCaptcha(captcha);

        assertEquals(300, config.getCaptcha().getWidth());
    }
}
