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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    private final AdminRoleMapper adminRoleMapper;
    private final AdminUserAndRoleMapper adminUserAndRoleMapper;
    private final AdminRoleAndPermissionMapper adminRoleAndPermissionMapper;
    private final AdminRbacMapper adminRbacMapper;

    public AdminRoleServiceImpl(AdminRoleMapper adminRoleMapper, AdminRoleAndPermissionMapper adminRoleAndPermissionMapper, AdminUserAndRoleMapper adminUserAndRoleMapper, AdminRbacMapper adminRbacMapper) {
        this.adminRoleMapper = adminRoleMapper;
        this.adminRoleAndPermissionMapper = adminRoleAndPermissionMapper;
        this.adminUserAndRoleMapper = adminUserAndRoleMapper;
        this.adminRbacMapper = adminRbacMapper;
    }

    /**
     * 新增角色
     *
     * @param role
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    public void addRole(Role role) {
        validateField(role);
        isSuccess(adminRoleMapper.addRole(role));
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteRole(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        if (id == 0) {
            throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
        }

        adminUserAndRoleMapper.deleteUserAndRoleByRoleId(id);
        adminRoleAndPermissionMapper.deleteRoleAndPermissionByRoleId(id);
        isSuccess(adminRoleMapper.deleteRole(id));
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
            if (id == 1) {
                throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
            }
        }

        adminUserAndRoleMapper.batchDeleteUserAndRoleByRoleId(ids);
        adminRoleAndPermissionMapper.batchDeleteRoleAndPermissionByRoleId(ids);
        isSuccess(adminRoleMapper.batchDeleteRole(ids));
    }

    /**
     * 修改角色
     *
     * @param role
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateRole(Role role) {
        if (role.getId() == 1) {
            throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
        }

        validateField(role);
        isSuccess(adminRoleMapper.updateRole(role));
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
            for (UserVo userVo : list) {
                userVo.setPermissions(adminRbacMapper.getPermissionList(userVo.getId()));
                userVo.setRoles(adminRbacMapper.getRoleList(userVo.getId()));
            }
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
            for (UserVo userVo : list) {
                userVo.setPermissions(adminRbacMapper.getPermissionList(userVo.getId()));
                userVo.setRoles(adminRbacMapper.getRoleList(userVo.getId()));
            }
        }
        return PageInfo.of(list);
    }

    /**
     * 分配角色
     *
     * @param assignRoleDTO
     */
    @Override
    public void assignRole(AssignRoleDTO assignRoleDTO) {
        if (ObjectUtil.isEmpty(assignRoleDTO.getRoleId()) || ObjectUtil.isEmpty(assignRoleDTO.getUserId())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        for (Long i : assignRoleDTO.getUserId()) {
            if (adminUserAndRoleMapper.validateIsExistByUserId(i, assignRoleDTO.getRoleId()) != 0) {
                throw new BusinessException(4004, "序号为:" + i + "的用户已分配当前角色");
            }
        }

        isSuccess(adminUserAndRoleMapper.assignRole(assignRoleDTO.getUserId(), assignRoleDTO.getRoleId()));
    }

    /**
     * 移除角色
     *
     * @param assignRoleDTO
     */
    @Override
    public void removeRole(AssignRoleDTO assignRoleDTO) {
        if (ObjectUtil.isEmpty(assignRoleDTO.getRoleId()) || ObjectUtil.isEmpty(assignRoleDTO.getUserId())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        for (Long l : assignRoleDTO.getUserId()) {
            if (l == 1) {
                throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
            }
        }

        isSuccess(adminUserAndRoleMapper.removeRole(assignRoleDTO.getUserId(), assignRoleDTO.getRoleId()));
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
     * @param role
     */
    public void validateField(Role role) {
        if (ObjectUtil.isEmpty(role.getRoleCode()) ||
                ObjectUtil.isEmpty(role.getRoleName()) ||
                ObjectUtil.isEmpty(role.getDescription())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
    }
}
