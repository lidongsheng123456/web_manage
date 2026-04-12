package com.example.controller;

import com.example.common.config.AppConfig;
import com.example.common.entity.Result;
import com.example.common.exception.BusinessException;
import com.example.controller.common.FileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FileController 文件接口测试")
class FileControllerTest {

    private AppConfig appConfig;
    private FileController controller;

    @BeforeEach
    void setUp() throws Exception {
        appConfig = new AppConfig();
        controller = new FileController(appConfig);

        // 通过反射设置私有字段
        setField(controller, "port", "8080");
        setField(controller, "ip", "localhost");
        setField(controller, "filePath", System.getProperty("java.io.tmpdir") + "/test-upload/");
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    @DisplayName("imgPath 路径包含 .. 应忽略")
    void imgPathTraversal() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.imgPath("../etc/passwd", response);
        // 不应抛异常，直接返回
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("imgPath 路径包含 / 应忽略")
    void imgPathSlash() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.imgPath("test/file.txt", response);
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("imgPath 路径为空应忽略")
    void imgPathEmpty() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.imgPath("", response);
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("delFile 路径包含 .. 应抛出异常")
    void delFileTraversal() {
        assertThrows(BusinessException.class, () -> controller.delFile("../test"));
    }

    @Test
    @DisplayName("delFile 路径包含反斜杠应抛出异常")
    void delFileBackSlash() {
        assertThrows(BusinessException.class, () -> controller.delFile("test\\file"));
    }

    @Test
    @DisplayName("delFile 文件不存在应抛出异常")
    void delFileNotExist() {
        assertThrows(BusinessException.class, () -> controller.delFile("nonexistent-file.txt"));
    }

    @Test
    @DisplayName("upload 空文件名应抛出异常")
    void uploadEmptyName() {
        org.springframework.mock.web.MockMultipartFile file =
                new org.springframework.mock.web.MockMultipartFile("file", "", "text/plain", new byte[0]);

        assertThrows(BusinessException.class, () -> controller.upload(file));
    }

    @Test
    @DisplayName("upload 不允许的扩展名应抛出异常")
    void uploadDisallowedExt() {
        org.springframework.mock.web.MockMultipartFile file =
                new org.springframework.mock.web.MockMultipartFile("file", "test.exe", "application/octet-stream", new byte[]{1});

        assertThrows(BusinessException.class, () -> controller.upload(file));
    }
}
