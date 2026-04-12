package com.example.config;

import com.example.common.redis.RedisCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * RedisCache 工具类单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RedisCache 工具类测试")
class RedisCacheTest {

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    @Mock
    private ListOperations listOperations;

    @Mock
    private SetOperations setOperations;

    @Mock
    private HashOperations hashOperations;

    @Mock
    private BoundSetOperations boundSetOperations;

    private RedisCache redisCache;

    @BeforeEach
    void setUp() {
        redisCache = new RedisCache(redisTemplate);
    }

    @Test
    @DisplayName("getRedisTemplate 应返回注入的实例")
    void getRedisTemplateShouldReturnInjectedInstance() {
        assertSame(redisTemplate, redisCache.getRedisTemplate());
    }

    // ========== setCacheObject ==========

    @Test
    @DisplayName("setCacheObject 无过期时间")
    void setCacheObjectWithoutExpiry() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisCache.setCacheObject("key1", "value1");

        verify(valueOperations).set("key1", "value1");
    }

    @Test
    @DisplayName("setCacheObject 带过期时间")
    void setCacheObjectWithExpiry() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisCache.setCacheObject("key2", "value2", 30, TimeUnit.MINUTES);

        verify(valueOperations).set("key2", "value2", 30, TimeUnit.MINUTES);
    }

    // ========== getCacheObject ==========

    @Test
    @DisplayName("getCacheObject 命中缓存")
    void getCacheObjectHit() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("key1")).thenReturn("value1");

        String result = redisCache.getCacheObject("key1");

        assertEquals("value1", result);
    }

    @Test
    @DisplayName("getCacheObject 未命中返回 null")
    void getCacheObjectMiss() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("missing")).thenReturn(null);

        Object result = redisCache.getCacheObject("missing");

        assertNull(result);
    }

    // ========== deleteObject ==========

    @Test
    @DisplayName("deleteObject 单个 key")
    void deleteObjectSingleKey() {
        when(redisTemplate.delete("key1")).thenReturn(true);

        assertTrue(redisCache.deleteObject("key1"));
        verify(redisTemplate).delete("key1");
    }

    @Test
    @DisplayName("deleteObject 集合 key")
    void deleteObjectCollection() {
        Collection<String> keys = List.of("k1", "k2");
        when(redisTemplate.delete(keys)).thenReturn(2L);

        assertTrue(redisCache.deleteObject(keys));
    }

    // ========== expire ==========

    @Test
    @DisplayName("expire 设置过期时间（秒）")
    void expireWithSeconds() {
        when(redisTemplate.expire("key1", 60, TimeUnit.SECONDS)).thenReturn(true);

        assertTrue(redisCache.expire("key1", 60));
    }

    @Test
    @DisplayName("expire 设置过期时间（自定义单位）")
    void expireWithCustomUnit() {
        when(redisTemplate.expire("key1", 5, TimeUnit.MINUTES)).thenReturn(true);

        assertTrue(redisCache.expire("key1", 5, TimeUnit.MINUTES));
    }

    // ========== getExpire ==========

    @Test
    @DisplayName("getExpire 获取有效时间")
    void getExpire() {
        when(redisTemplate.getExpire("key1")).thenReturn(300L);

        assertEquals(300L, redisCache.getExpire("key1"));
    }

    // ========== hasKey ==========

    @Test
    @DisplayName("hasKey 存在返回 true")
    void hasKeyExists() {
        when(redisTemplate.hasKey("key1")).thenReturn(true);

        assertTrue(redisCache.hasKey("key1"));
    }

    @Test
    @DisplayName("hasKey 不存在返回 false")
    void hasKeyNotExists() {
        when(redisTemplate.hasKey("missing")).thenReturn(false);

        assertFalse(redisCache.hasKey("missing"));
    }

    // ========== List 操作 ==========

    @Test
    @DisplayName("setCacheList 缓存列表")
    void setCacheList() {
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        List<String> list = List.of("a", "b", "c");
        when(listOperations.rightPushAll("listKey", list)).thenReturn(3L);

        long count = redisCache.setCacheList("listKey", list);

        assertEquals(3L, count);
    }

    @Test
    @DisplayName("setCacheList 返回 null 时计数为 0")
    void setCacheListNullCount() {
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        List<String> list = List.of("a");
        when(listOperations.rightPushAll("listKey", list)).thenReturn(null);

        assertEquals(0, redisCache.setCacheList("listKey", list));
    }

    @Test
    @DisplayName("getCacheList 获取列表")
    void getCacheList() {
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.range("listKey", 0, -1)).thenReturn(List.of("a", "b"));

        List<String> result = redisCache.getCacheList("listKey");

        assertEquals(2, result.size());
    }

    // ========== Set 操作 ==========

    @Test
    @DisplayName("setCacheSet 缓存 Set")
    void setCacheSet() {
        when(redisTemplate.boundSetOps("setKey")).thenReturn(boundSetOperations);
        Set<String> dataSet = new LinkedHashSet<>(List.of("x", "y"));

        BoundSetOperations result = redisCache.setCacheSet("setKey", dataSet);

        assertNotNull(result);
        verify(boundSetOperations, times(2)).add(anyString());
    }

    @Test
    @DisplayName("getCacheSet 获取 Set")
    void getCacheSet() {
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
        when(setOperations.members("setKey")).thenReturn(Set.of("x", "y"));

        Set<String> result = redisCache.getCacheSet("setKey");

        assertEquals(2, result.size());
    }

    // ========== Map 操作 ==========

    @Test
    @DisplayName("setCacheMap 缓存 Map")
    void setCacheMap() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        Map<String, String> map = Map.of("f1", "v1");

        redisCache.setCacheMap("mapKey", map);

        verify(hashOperations).putAll("mapKey", map);
    }

    @Test
    @DisplayName("setCacheMap null 不执行操作")
    void setCacheMapNull() {
        redisCache.setCacheMap("mapKey", null);
        // opsForHash should never be called
        verify(redisTemplate, never()).opsForHash();
    }

    @Test
    @DisplayName("getCacheMap 获取 Map")
    void getCacheMap() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("mapKey")).thenReturn(Map.of("f1", "v1"));

        Map<String, String> result = redisCache.getCacheMap("mapKey");

        assertEquals("v1", result.get("f1"));
    }

    @Test
    @DisplayName("setCacheMapValue 设置 Hash 字段")
    void setCacheMapValue() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        redisCache.setCacheMapValue("mapKey", "field", "value");

        verify(hashOperations).put("mapKey", "field", "value");
    }

    @Test
    @DisplayName("getCacheMapValue 获取 Hash 字段")
    void getCacheMapValue() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.get("mapKey", "field")).thenReturn("value");

        String result = redisCache.getCacheMapValue("mapKey", "field");

        assertEquals("value", result);
    }

    @Test
    @DisplayName("deleteCacheMapValue 删除 Hash 字段")
    void deleteCacheMapValue() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.delete("mapKey", "field")).thenReturn(1L);

        assertTrue(redisCache.deleteCacheMapValue("mapKey", "field"));
    }

    // ========== keys ==========

    @Test
    @DisplayName("keys 模糊查询")
    void keys() {
        Set<String> keySet = Set.of("notice:1", "notice:2");
        when(redisTemplate.keys("notice:*")).thenReturn(keySet);

        Collection<String> result = redisCache.keys("notice:*");

        assertEquals(2, result.size());
    }
}
