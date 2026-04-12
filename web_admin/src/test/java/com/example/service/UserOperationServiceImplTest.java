package com.example.service;

import com.example.system.mapper.UserOperationMapper;
import com.example.system.service.impl.UserOperationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserOperationServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserOperationServiceImpl 用户操作服务测试")
class UserOperationServiceImplTest {

    @Mock
    private UserOperationMapper userOperationMapper;

    private UserOperationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserOperationServiceImpl(userOperationMapper);
    }

    @Test
    @DisplayName("service 应正确初始化")
    void serviceInitialized() {
        assertNotNull(service);
    }
}
