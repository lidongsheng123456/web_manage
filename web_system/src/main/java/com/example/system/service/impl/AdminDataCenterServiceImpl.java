package com.example.system.service.impl;

import com.example.system.domain.vo.DataVo;
import com.example.system.mapper.AdminDataCenterMapper;
import com.example.system.service.AdminDataCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDataCenterServiceImpl implements AdminDataCenterService {

    private final AdminDataCenterMapper adminDataCenterMapper;

    /**
     * 查询数据
     *
     * @return
     */
    @Override
    public DataVo queryData() {
        return DataVo.builder()
                .user(adminDataCenterMapper.userCount())
                .notice(adminDataCenterMapper.noticeCount())
                .operLog(adminDataCenterMapper.operLogCount())
                .permission(adminDataCenterMapper.permissionCount())
                .frontUser(adminDataCenterMapper.frontUserCount())
                .role(adminDataCenterMapper.roleCount())
                .dictData(adminDataCenterMapper.dictDataCount())
                .comQuery(adminDataCenterMapper.comQueryCount())
                .rolePermission(adminDataCenterMapper.rolePermissionCount())
                .userRole(adminDataCenterMapper.userRoleCount())
                .totalUser(adminDataCenterMapper.totalUserCount())
                .totalNotice(adminDataCenterMapper.totalNoticeCount())
                .totalOperLog(adminDataCenterMapper.totalOperLogCount())
                .totalPermission(adminDataCenterMapper.totalPermissionCount())
                .totalFrontUser(adminDataCenterMapper.totalFrontUserCount())
                .totalRole(adminDataCenterMapper.totalRoleCount())
                .totalDictData(adminDataCenterMapper.totalDictDataCount())
                .totalComQuery(adminDataCenterMapper.totalComQueryCount())
                .totalRolePermission(adminDataCenterMapper.totalRolePermissionCount())
                .totalUserRole(adminDataCenterMapper.totalUserRoleCount())
                .build();
    }
}
