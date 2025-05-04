package com.example.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.exception.BusinessException;
import com.example.common.interface_constants.Constants;
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
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminUserMapper;
    private final AdminNoticeMapper adminNoticeMapper;
    private final AdminUserAndRoleMapper adminUserAndRoleMapper;
    private final AdminRbacMapper adminRbacMapper;

    /**
     * 新增用户
     *
     * @param user
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    @Transactional
    public void addUser(User user) {
        validateField(user);
        user.setPassword(DigestUtils.md5DigestAsHex(Constants.DEFAULT_PAD.getBytes()));
        isSuccess(adminUserMapper.addUser(user));
        isSuccess(adminUserAndRoleMapper.addUserAndRoleId(user.getId(), RoleEnum.USER.roleId));
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        if (id == 1) {
            throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
        }

        adminNoticeMapper.deleteNoticeByUserId(id);
        adminUserAndRoleMapper.deleteUserAndRoleByUserId(id);
        isSuccess(adminUserMapper.deleteUser(id));
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
            if (id == 1) {
                throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
            }
        }

        adminNoticeMapper.batchDeleteNoticeByUserIds(ids);
        adminUserAndRoleMapper.batchDeleteUserAndRoleByUserId(ids);
        isSuccess(adminUserMapper.batchDeleteUser(ids));
    }

    /**
     * 修改用户
     *
     * @param user
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateUser(User user) {
        if (user.getId() == 1) {
            throw new BusinessException(ResultCodeEnum.BAN_OPERATE_SUPER_ADMIN_ERROR);
        }

        validateField(user);
        isSuccess(adminUserMapper.updateUser(user));
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
            for (UserVo userVo : list) {
                userVo.setPermissions(adminRbacMapper.getPermissionList(userVo.getId()));
                userVo.setRoles(adminRbacMapper.getRoleList(userVo.getId()));
            }
        }
        return PageInfo.of(list);
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
     * @param user
     */
    public void validateField(User user) {
        if (ObjectUtil.isEmpty(user.getUsername()) ||
                ObjectUtil.isEmpty(user.getName()) ||
                ObjectUtil.isEmpty(user.getPhone()) ||
                ObjectUtil.isEmpty(user.getEmail())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
    }
}
