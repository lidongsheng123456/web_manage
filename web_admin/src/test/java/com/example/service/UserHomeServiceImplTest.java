package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.config.AppConfig;
import com.example.common.redis.RedisCache;
import com.example.system.domain.Notice;
import com.example.system.mapper.UserHomMapper;
import com.example.system.service.impl.UserHomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * UserHomeServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserHomeServiceImpl 前台首页服务测试")
class UserHomeServiceImplTest {

    @Mock
    private UserHomMapper userHomMapper;

    @Mock
    private RedisCache redisCache;

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    private AppConfig appConfig;
    private UserHomeServiceImpl userHomeService;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        userHomeService = new UserHomeServiceImpl(userHomMapper, redisCache, appConfig);
    }

    @Test
    @DisplayName("queryNotice 空 ID 列表返回空集合")
    void queryNoticeEmptyIds() {
        Notice notice = new Notice();
        when(userHomMapper.queryNotice(notice)).thenReturn(List.of());

        List<Object> result = userHomeService.queryNotice(notice);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("queryNotice 全部缓存命中")
    void queryNoticeAllCacheHit() {
        Notice notice = new Notice();
        when(userHomMapper.queryNotice(notice)).thenReturn(List.of("1", "2"));

        Notice n1 = new Notice();
        n1.setId(1L);
        n1.setNoticeTitle("通知1");
        Notice n2 = new Notice();
        n2.setId(2L);
        n2.setNoticeTitle("通知2");

        when(redisCache.getCacheObject("notice:1")).thenReturn(n1);
        when(redisCache.getCacheObject("notice:2")).thenReturn(n2);

        List<Object> result = userHomeService.queryNotice(notice);

        assertEquals(2, result.size());
        verify(userHomMapper, never()).batchQueryNotice(anyList());
    }

    @Test
    @DisplayName("queryNotice 部分缓存未命中应查数据库并回填")
    void queryNoticePartialCacheMiss() {
        Notice notice = new Notice();
        when(userHomMapper.queryNotice(notice)).thenReturn(List.of("1", "2"));

        Notice n1 = new Notice();
        n1.setId(1L);
        n1.setNoticeTitle("通知1");

        when(redisCache.getCacheObject("notice:1")).thenReturn(n1);
        when(redisCache.getCacheObject("notice:2")).thenReturn(null);

        Notice n2 = new Notice();
        n2.setId(2L);
        n2.setNoticeTitle("通知2");
        when(userHomMapper.batchQueryNotice(anyList())).thenReturn(List.of(n2));

        List<Object> result = userHomeService.queryNotice(notice);

        // 验证批量查询被调用
        verify(userHomMapper).batchQueryNotice(anyList());
        // 验证 setCacheObject 被调用回填缓存
        verify(redisCache).setCacheObject(eq("notice:2"), eq(n2), anyInt(), any());
    }

    @Test
    @DisplayName("queryNotice 使用自定义 noticePrefix")
    void queryNoticeCustomPrefix() {
        appConfig.getCache().setNoticePrefix("n:");

        Notice notice = new Notice();
        when(userHomMapper.queryNotice(notice)).thenReturn(List.of("1"));

        Notice n1 = new Notice();
        n1.setId(1L);
        when(redisCache.getCacheObject("n:1")).thenReturn(n1);

        List<Object> result = userHomeService.queryNotice(notice);

        assertEquals(1, result.size());
        verify(redisCache).getCacheObject("n:1");
    }
}
