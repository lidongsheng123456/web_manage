package com.example.framework.web.security;

import cn.dev33.satoken.stp.StpInterface;
import com.example.common.config.AppConfig;
import com.example.common.redis.RedisCache;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminUserAndRoleMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final AdminRbacMapper adminRbacMapper;
    private final AdminUserAndRoleMapper adminUserAndRoleMapper;
    private final RedisCache redisCache;
    private final AppConfig appConfig;

    public StpInterfaceImpl(AdminRbacMapper adminRbacMapper, AdminUserAndRoleMapper adminUserAndRoleMapper,
                            RedisCache redisCache, AppConfig appConfig) {
        this.adminRbacMapper = adminRbacMapper;
        this.adminUserAndRoleMapper = adminUserAndRoleMapper;
        this.redisCache = redisCache;
        this.appConfig = appConfig;
    }

    /**
     * 返回一个账号所拥有的权限码集合（带 Redis 缓存）
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String cacheKey = appConfig.getCache().getPermPrefix() + loginId;
        List<String> cached = redisCache.getCacheObject(cacheKey);
        if (cached != null) {
            return cached;
        }
        List<String> list = new ArrayList<>();
        List<Map<String, Object>> maps = adminRbacMapper.getPermissionList(Long.valueOf((String) loginId));
        maps.forEach(m -> list.add((String) m.get("permission_code")));
        redisCache.setCacheObject(cacheKey, list, appConfig.getCache().getPermTtlMinutes(), TimeUnit.MINUTES);
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合（带 Redis 缓存）
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String cacheKey = appConfig.getCache().getRolePrefix() + loginId;
        List<String> cached = redisCache.getCacheObject(cacheKey);
        if (cached != null) {
            return cached;
        }
        List<String> list = new ArrayList<>();
        List<Map<String, Object>> maps = adminRbacMapper.getRoleList(Long.valueOf((String) loginId));
        maps.forEach(m -> list.add((String) m.get("role_code")));
        redisCache.setCacheObject(cacheKey, list, appConfig.getCache().getPermTtlMinutes(), TimeUnit.MINUTES);
        return list;
    }

    /**
     * 清除指定用户的权限/角色缓存（角色或权限变更时调用）
     */
    public void clearCache(Long userId) {
        redisCache.deleteObject(appConfig.getCache().getPermPrefix() + userId);
        redisCache.deleteObject(appConfig.getCache().getRolePrefix() + userId);
    }

    /**
     * 根据角色ID列表清除对应用户的权限/角色缓存
     */
    public void clearCacheByRoleIds(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            List<Long> userIds = adminUserAndRoleMapper.getUserIdsByRoleId(roleId);
            if (userIds != null) {
                userIds.forEach(this::clearCache);
            }
        }
    }

}
