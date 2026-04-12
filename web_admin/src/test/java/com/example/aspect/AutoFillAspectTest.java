package com.example.aspect;

import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.framework.aspect.AutoFillAspect;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AutoFillAspect 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AutoFillAspect 自动填充切面测试")
class AutoFillAspectTest {

    private AutoFillAspect autoFillAspect;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Data
    public static class SampleEntity {
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }

    @AutoFill(BusinessType.INSERT)
    public void mockInsertMethod(SampleEntity entity) {}

    @AutoFill(BusinessType.UPDATE)
    public void mockUpdateMethod(SampleEntity entity) {}

    @BeforeEach
    void setUp() {
        autoFillAspect = new AutoFillAspect();
    }

    @Test
    @DisplayName("INSERT 操作应填充 createTime 和 updateTime")
    void autoFillInsert() throws Exception {
        SampleEntity entity = new SampleEntity();
        Method method = this.getClass().getMethod("mockInsertMethod", SampleEntity.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{entity});

        autoFillAspect.autoFill(joinPoint);

        assertNotNull(entity.getCreateTime());
        assertNotNull(entity.getUpdateTime());
    }

    @Test
    @DisplayName("UPDATE 操作应只填充 updateTime")
    void autoFillUpdate() throws Exception {
        SampleEntity entity = new SampleEntity();
        Method method = this.getClass().getMethod("mockUpdateMethod", SampleEntity.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{entity});

        autoFillAspect.autoFill(joinPoint);

        assertNull(entity.getCreateTime());
        assertNotNull(entity.getUpdateTime());
    }

    @Test
    @DisplayName("参数为空数组应直接返回不抛异常")
    void autoFillEmptyArgs() throws Exception {
        Method method = this.getClass().getMethod("mockInsertMethod", SampleEntity.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{});

        assertDoesNotThrow(() -> autoFillAspect.autoFill(joinPoint));
    }
}
