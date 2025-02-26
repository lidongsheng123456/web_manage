package com.example.framework.web.security;

import cn.dev33.satoken.stp.StpInterface;
import com.example.system.mapper.AdminRbacMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {
    private final AdminRbacMapper adminRbacMapper;

    public StpInterfaceImpl(AdminRbacMapper adminRbacMapper) {
        this.adminRbacMapper = adminRbacMapper;
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        List<Map<String, Object>> maps = adminRbacMapper.getPermissionList(Long.valueOf((String) loginId));//[{permission_code=/admin/notice/add}, {permission_code=/admin/notice/delete}, {permission_code=/admin/notice/update}, {permission_code=/admin/notice/querry}]
        maps.forEach(stringObjectMap -> list.add((String) stringObjectMap.get("permission_code")));
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<String>();
        List<Map<String, Object>> maps = adminRbacMapper.getRoleList(Long.valueOf((String) loginId));// [{role_code=super_admin}]
        maps.forEach(stringObjectMap -> list.add((String) stringObjectMap.get("role_code")));
        return list;
    }

}
