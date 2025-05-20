package com.example.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Permission;
import com.example.system.domain.Role;
import com.example.system.domain.dto.AssignPermissionDTO;
import com.example.system.mapper.AdminPermissionMapper;
import com.example.system.mapper.AdminRoleAndPermissionMapper;
import com.example.system.service.AdminPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPermissionServiceImpl implements AdminPermissionService {

    private final AdminPermissionMapper adminPermissionMapper;
    private final AdminRoleAndPermissionMapper adminRoleAndPermissionMapper;

    /**
     * 新增权限
     *
     * @param permission
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    public void addPermission(Permission permission) {
        validateField(permission);
        isSuccess(adminPermissionMapper.addPermission(permission));
    }

    /**
     * 批量删除权限
     *
     * @param ids
     */
    @Override
    public void batchDeletePermission(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        adminRoleAndPermissionMapper.batchDeleteRoleAndPermissionByRoleId(ids);
        isSuccess(adminPermissionMapper.batchDeletePermission(ids));
    }

    /**
     * 修改权限
     *
     * @param permission
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updatePermission(Permission permission) {
        validateField(permission);
        isSuccess(adminPermissionMapper.updatePermission(permission));
    }

    /**
     * 查询权限
     *
     * @param permission
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Permission> queryPermission(Permission permission, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Permission> list = adminPermissionMapper.queryPermission(permission);
        return PageInfo.of(list);
    }

    /**
     * 根据id查询权限
     *
     * @param id
     * @return
     */
    @Override
    public Permission queryPermissionById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        return adminPermissionMapper.queryPermissionById(id);
    }

    /**
     * 查询全部权限
     *
     * @return
     */
    @Override
    public List<Permission> queryAllPermission() {
        return adminPermissionMapper.queryAllPermission();
    }

    /**
     * 查询没有分配当前权限的角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Role> queryRoleNotPermissionId(Role role, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Role> list = adminPermissionMapper.queryRoleNotPermissionId(role);
        return PageInfo.of(list);
    }

    /**
     * 查询已经分配当前权限的角色
     *
     * @param role
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Role> queryRoleByPermissionId(Role role, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Role> list = adminPermissionMapper.queryRoleByPermissionId(role);
        return PageInfo.of(list);
    }

    /**
     * 分配权限
     *
     * @param assignPermissionDTO
     */
    @Override
    public void assignPermission(AssignPermissionDTO assignPermissionDTO) {
        if (ObjectUtil.isEmpty(assignPermissionDTO.getRoleId()) || ObjectUtil.isEmpty(assignPermissionDTO.getPermissionId())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        for (Long i : assignPermissionDTO.getRoleId()) {
            if (adminRoleAndPermissionMapper.validateIsExistByRoleId(i, assignPermissionDTO.getPermissionId()) != 0) {
                throw new BusinessException(4004, "序号为:" + i + "的角色已分配当前权限");
            }
        }

        isSuccess(adminRoleAndPermissionMapper.assignPermission(assignPermissionDTO.getRoleId(), assignPermissionDTO.getPermissionId()));
    }

    /**
     * 移除权限
     *
     * @param assignPermissionDTO
     */
    @Override
    public void removePermission(AssignPermissionDTO assignPermissionDTO) {
        if (ObjectUtil.isEmpty(assignPermissionDTO.getRoleId()) || ObjectUtil.isEmpty(assignPermissionDTO.getPermissionId())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminRoleAndPermissionMapper.removePermission(assignPermissionDTO.getRoleId(), assignPermissionDTO.getPermissionId()));
    }

    /**
     * 是否成功
     *
     * @param i
     */
    public void isSuccess(Integer i) {
        if (i == 0) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 验证字段
     *
     * @param permission
     */
    public void validateField(Permission permission) {
        if (ObjectUtil.isEmpty(permission.getPermissionCode()) ||
                ObjectUtil.isEmpty(permission.getPermissionName()) ||
                ObjectUtil.isEmpty(permission.getDescription())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
    }
}
