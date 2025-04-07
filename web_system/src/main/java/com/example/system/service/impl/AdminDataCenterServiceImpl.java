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
                .build();
    }
}
