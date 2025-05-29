package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.redis.RedisCache;
import com.example.system.domain.Notice;
import com.example.system.mapper.UserHomMapper;
import com.example.system.service.UserHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings(value = {"unchecked"})
public class UserHomeServiceImpl implements UserHomeService {
    private final UserHomMapper userHomMapper;

    private final RedisCache redisCache;

    /**
     * 列表缓存通知
     * 查询对象 ID 列表，再缓存每个对象条目 这个方案比较灵活，
     * 搜索的分页结果只包含业务对象 ID ，对象的详细资料需要从缓存 + MySQL 中获取。
     *
     * @return
     */
    @Override
    public List<Object> queryNotice(Notice notice) {
        // 返回结果
        List<Object> result = new ArrayList<>();

        // 1.从数据库中查询分页ID列表
        List<String> noticeIdList = userHomMapper.queryNotice(notice);
        if (noticeIdList.isEmpty()) {
            return List.of();
        }

        // 2.批量从缓存中获取产品项目
        Map<String, Object> cacheNoticeMap = new HashMap<>();
        for (String key : noticeIdList) {
            Object value = redisCache.getCacheObject(key);
            if (value != null) {
                cacheNoticeMap.put(key, value);
            }
        }

        // 3.组装没有命中
        ArrayList<String> noHitIdList = new ArrayList<>();
        for (String noticeId : noticeIdList) {
            if (!cacheNoticeMap.containsKey(noticeId)) {
                noHitIdList.add(noticeId);
            }
        }

        // 4.将没有命中缓存的商品，从数据库中查询出来，然后加载到缓存中
        if (ObjectUtil.isNotEmpty(noHitIdList)) {
            List<Notice> noHitNoticeList = userHomMapper.batchQueryNotice(noHitIdList);
            // 将没有命中的文化项目加入到缓存中
            Map<String, Notice> noHitNoticeMap = noHitNoticeList.stream().collect(Collectors.toMap(notice1 -> notice1.getId().toString(), Function.identity()));
            redisCache.redisTemplate.opsForValue().multiSet(noHitNoticeMap);
            cacheNoticeMap.putAll(noHitNoticeMap);
        }

        // 5.最后组装
        for (String noticeId : noticeIdList) {
            Object culture = cacheNoticeMap.get(noticeId);
            if (culture != null) {
                result.add(culture);
            }
        }

        return result;
    }
}
