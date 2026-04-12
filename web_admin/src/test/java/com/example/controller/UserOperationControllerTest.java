package com.example.controller;

import com.example.controller.user.UserOperationController;
import com.example.system.service.UserOperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserOperationController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserOperationController 用户操作接口测试")
class UserOperationControllerTest {

    @Mock
    private UserOperationService userOperationService;

    private UserOperationController controller;

    @BeforeEach
    void setUp() {
        controller = new UserOperationController(userOperationService);
    }

    @Test
    @DisplayName("controller 应正确初始化")
    void controllerInitialized() {
        assertNotNull(controller);
    }
}
