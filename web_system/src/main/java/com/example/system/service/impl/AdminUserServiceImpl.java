package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.config.AppConfig;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.User;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.AdminNoticeMapper;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminUserAndRoleMapper;
import com.example.system.mapper.AdminUserMapper;
import com.example.system.service.AdminUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.crypto.digest.BCrypt;

import static com.example.common.util.ServiceUtil.checkSuccess;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminUserMapper;
    private final AdminNoticeMapper adminNoticeMapper;
    private final AdminUserAndRoleMapper adminUserAndRoleMapper;
    private final AdminRbacMapper adminRbacMapper;
    private final AppConfig appConfig;

    /**
     * 新增用户
     *
     * @param user
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    @Transactional
    public void addUser(User user) {
        user.setPassword(BCrypt.hashpw(appConfig.getDefaultPassword()));
        checkSuccess(adminUserMapper.addUser(user));
        checkSuccess(adminUserAndRoleMapper.addUserAndRoleId(user.getId(), RoleEnum.USER.roleId));
    }

    /**
     * 批量删除用户
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleteUser(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        for (Long id : ids) {
            if (Objects.equals(id, 1L)) {
                throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
            }
        }

        adminNoticeMapper.batchDeleteNoticeByUserIds(ids);
        adminUserAndRoleMapper.batchDeleteUserAndRoleByUserId(ids);
        checkSuccess(adminUserMapper.batchDeleteUser(ids));
    }

    /**
     * 修改用户
     *
     * @param user
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateUser(User user) {
        if (Objects.equals(user.getId(), 1L)) {
            throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
        }

        checkSuccess(adminUserMapper.updateUser(user));
    }

    /**
     * 查询用户
     *
     * @param user
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UserVo> queryUser(UserVo user, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<UserVo> list = adminUserMapper.queryUser(user);
        if (ObjectUtil.isNotEmpty(list)) {
            fillPermissionsAndRoles(list);
        }
        return PageInfo.of(list);
    }

    /**
     * 批量填充用户权限和角色（解决 N+1 查询问题）
     */
    private void fillPermissionsAndRoles(List<UserVo> list) {
        List<Long> userIds = list.stream().map(UserVo::getId).toList();
        // 批量查询权限和角色（各1次SQL）
        List<Map<String, Object>> allPerms = adminRbacMapper.getBatchPermissionList(userIds);
        List<Map<String, Object>> allRoles = adminRbacMapper.getBatchRoleList(userIds);
        // 按 user_id 分组
        Map<Long, List<Map<String, Object>>> permMap = allPerms.stream()
                .collect(java.util.stream.Collectors.groupingBy(m -> ((Number) m.get("user_id")).longValue()));
        Map<Long, List<Map<String, Object>>> roleMap = allRoles.stream()
                .collect(java.util.stream.Collectors.groupingBy(m -> ((Number) m.get("user_id")).longValue()));
        for (UserVo vo : list) {
            vo.setPermissions(permMap.getOrDefault(vo.getId(), List.of()));
            vo.setRoles(roleMap.getOrDefault(vo.getId(), List.of()));
        }
    }

    /**
     * 查询全部用户
     *
     * @return
     */
    @Override
    public List<User> queryAllUser() {
        return adminUserMapper.queryAllUser();
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public UserVo queryUserById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        User user = adminUserMapper.queryUserById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setPermissions(adminRbacMapper.getPermissionList(userVo.getId()));
        userVo.setRoles(adminRbacMapper.getRoleList(userVo.getId()));
        return userVo;
    }

}
