package com.example.service;

import com.example.common.config.AppConfig;
import com.example.common.redis.RedisCache;
import com.example.framework.web.security.StpInterfaceImpl;
import com.example.system.mapper.AdminRbacMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * StpInterfaceImpl 权限缓存单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StpInterfaceImpl 权限/角色缓存测试")
class StpInterfaceImplTest {

    @Mock
    private AdminRbacMapper adminRbacMapper;

    @Mock
    private RedisCache redisCache;

    private AppConfig appConfig;
    private StpInterfaceImpl stpInterface;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        stpInterface = new StpInterfaceImpl(adminRbacMapper, redisCache, appConfig);
    }

    @Test
    @DisplayName("getPermissionList 缓存命中时不查数据库")
    void getPermissionListCacheHit() {
        List<String> cached = List.of("admin:user:add", "admin:user:query");
        when(redisCache.getCacheObject("sa:perm:1")).thenReturn(cached);

        List<String> result = stpInterface.getPermissionList("1", "login");

        assertEquals(2, result.size());
        verify(adminRbacMapper, never()).getPermissionList(anyLong());
    }

    @Test
    @DisplayName("getPermissionList 缓存未命中时查数据库并缓存")
    void getPermissionListCacheMiss() {
        when(redisCache.getCacheObject("sa:perm:1")).thenReturn(null);
        List<Map<String, Object>> dbResult = List.of(
                Map.of("permission_code", "admin:user:add"),
                Map.of("permission_code", "admin:user:query")
        );
        when(adminRbacMapper.getPermissionList(1L)).thenReturn(dbResult);

        List<String> result = stpInterface.getPermissionList("1", "login");

        assertEquals(2, result.size());
        assertTrue(result.contains("admin:user:add"));
        verify(redisCache).setCacheObject(eq("sa:perm:1"), anyList(), eq(30), eq(TimeUnit.MINUTES));
    }

    @Test
    @DisplayName("getRoleList 缓存命中时不查数据库")
    void getRoleListCacheHit() {
        List<String> cached = List.of("admin", "user");
        when(redisCache.getCacheObject("sa:role:1")).thenReturn(cached);

        List<String> result = stpInterface.getRoleList("1", "login");

        assertEquals(2, result.size());
        verify(adminRbacMapper, never()).getRoleList(anyLong());
    }

    @Test
    @DisplayName("getRoleList 缓存未命中时查数据库并缓存")
    void getRoleListCacheMiss() {
        when(redisCache.getCacheObject("sa:role:1")).thenReturn(null);
        List<Map<String, Object>> dbResult = List.of(
                Map.of("role_code", "admin")
        );
        when(adminRbacMapper.getRoleList(1L)).thenReturn(dbResult);

        List<String> result = stpInterface.getRoleList("1", "login");

        assertEquals(1, result.size());
        assertEquals("admin", result.get(0));
        verify(redisCache).setCacheObject(eq("sa:role:1"), anyList(), eq(30), eq(TimeUnit.MINUTES));
    }

    @Test
    @DisplayName("clearCache 应清除权限和角色缓存")
    void clearCache() {
        stpInterface.clearCache(1L);

        verify(redisCache).deleteObject("sa:perm:1");
        verify(redisCache).deleteObject("sa:role:1");
    }

    @Test
    @DisplayName("使用自定义前缀和 TTL")
    void customPrefixAndTtl() {
        appConfig.getCache().setPermPrefix("custom:perm:");
        appConfig.getCache().setRolePrefix("custom:role:");
        appConfig.getCache().setPermTtlMinutes(60);

        when(redisCache.getCacheObject("custom:perm:2")).thenReturn(null);
        when(adminRbacMapper.getPermissionList(2L)).thenReturn(List.of(Map.of("permission_code", "test")));

        stpInterface.getPermissionList("2", "login");

        verify(redisCache).setCacheObject(eq("custom:perm:2"), anyList(), eq(60), eq(TimeUnit.MINUTES));
    }
}
