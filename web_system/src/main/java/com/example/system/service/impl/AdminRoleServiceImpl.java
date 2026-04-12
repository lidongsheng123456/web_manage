package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignRoleDTO;
import com.example.system.domain.vo.UserVo;
import com.example.system.mapper.AdminRbacMapper;
import com.example.system.mapper.AdminRoleAndPermissionMapper;
import com.example.system.mapper.AdminRoleMapper;
import com.example.system.mapper.AdminUserAndRoleMapper;
import com.example.system.service.AdminRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.common.util.ServiceUtil.checkSuccess;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {

    private final AdminRoleMapper adminRoleMapper;
    private final AdminUserAndRoleMapper adminUserAndRoleMapper;
    private final AdminRoleAndPermissionMapper adminRoleAndPermissionMapper;
    private final AdminRbacMapper adminRbacMapper;

    /**
     * 新增角色
     *
     * @param role
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    public void addRole(Role role) {
        checkSuccess(adminRoleMapper.addRole(role));
    }

    /**
     * 批量删除角色
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleteRole(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        for (Long id : ids) {
            if (Objects.equals(id, 1L)) {
                throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
            }
        }

        adminUserAndRoleMapper.batchDeleteUserAndRoleByRoleId(ids);
        adminRoleAndPermissionMapper.batchDeleteRoleAndPermissionByRoleId(ids);
        checkSuccess(adminRoleMapper.batchDeleteRole(ids));
    }

    /**
     * 修改角色
     *
     * @param role
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateRole(Role role) {
        if (Objects.equals(role.getId(), 1L)) {
            throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
        }

        checkSuccess(adminRoleMapper.updateRole(role));
    }

    /**
     * 查询角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Role> queryRole(Role role, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Role> list = adminRoleMapper.queryRole(role);
        return PageInfo.of(list);
    }

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    @Override
    public Role queryRoleById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        return adminRoleMapper.queryRoleById(id);
    }

    /**
     * 查询全部角色
     *
     * @return
     */
    @Override
    public List<Role> queryAllRole() {
        return adminRoleMapper.queryAllRole();
    }

    /**
     * 查询没有分配当前角色的用户
     *
     * @param user
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UserVo> queryUserNotRoleId(UserVo user, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<UserVo> list = adminRoleMapper.queryUserNotRoleId(user);
        if (ObjectUtil.isNotEmpty(list)) {
            fillPermissionsAndRoles(list);
        }
        return PageInfo.of(list);
    }

    /**
     * 查询已经分配当前角色的用户
     *
     * @param user
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UserVo> queryUserByRoleId(UserVo user, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<UserVo> list = adminRoleMapper.queryUserByRoleId(user);
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
        List<Map<String, Object>> allPerms = adminRbacMapper.getBatchPermissionList(userIds);
        List<Map<String, Object>> allRoles = adminRbacMapper.getBatchRoleList(userIds);
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
     * 分配角色
     *
     * @param assignRoleDTO
     */
    @Override
    public void assignRole(AssignRoleDTO assignRoleDTO) {
        for (Long i : assignRoleDTO.getUserId()) {
            if (adminUserAndRoleMapper.validateIsExistByUserId(i, assignRoleDTO.getRoleId()) != 0) {
                throw new BusinessException(4004, "序号为:" + i + "的用户已分配当前角色");
            }
        }

        checkSuccess(adminUserAndRoleMapper.assignRole(assignRoleDTO.getUserId(), assignRoleDTO.getRoleId()));
    }

    /**
     * 移除角色
     *
     * @param assignRoleDTO
     */
    @Override
    public void removeRole(AssignRoleDTO assignRoleDTO) {
        for (Long l : assignRoleDTO.getUserId()) {
            if (Objects.equals(l, 1L)) {
                throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
            }
        }

        // 当用户只有最后一个角色时不允许操作
        for (Long userId : assignRoleDTO.getUserId()) {
            List<Map<Long, Long>> list = adminUserAndRoleMapper.getUserAndRoleByUserId(userId);
            if (list.size() == 1) {
                throw new BusinessException(4005, "序号为：" + userId + "的用户只剩余一个角色,禁止删除");
            }
        }

        checkSuccess(adminUserAndRoleMapper.removeRole(assignRoleDTO.getUserId(), assignRoleDTO.getRoleId()));
    }

}
