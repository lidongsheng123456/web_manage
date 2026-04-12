package com.example.service;

import com.example.system.domain.vo.DataCountTypeVo;
import com.example.system.domain.vo.DataVo;
import com.example.system.mapper.AdminDataCenterMapper;
import com.example.system.service.impl.AdminDataCenterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminDataCenterServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminDataCenterServiceImpl 数据中心服务测试")
class AdminDataCenterServiceImplTest {

    @Mock
    private AdminDataCenterMapper adminDataCenterMapper;

    private AdminDataCenterServiceImpl service;

    private final List<DataCountTypeVo> sampleList = List.of(new DataCountTypeVo("Mon", 10L));
    private final List<DataCountTypeVo> emptyList = List.of();

    @BeforeEach
    void setUp() {
        service = new AdminDataCenterServiceImpl(adminDataCenterMapper);
    }

    @Test
    @DisplayName("queryData 应组装所有统计数据")
    void queryDataSuccess() {
        when(adminDataCenterMapper.userCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.noticeCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.operLogCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.permissionCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.frontUserCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.roleCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.dictDataCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.comQueryCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.rolePermissionCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.userRoleCount()).thenReturn(sampleList);
        when(adminDataCenterMapper.totalUserCount()).thenReturn(100L);
        when(adminDataCenterMapper.totalNoticeCount()).thenReturn(50L);
        when(adminDataCenterMapper.totalOperLogCount()).thenReturn(200L);
        when(adminDataCenterMapper.totalPermissionCount()).thenReturn(80L);
        when(adminDataCenterMapper.totalFrontUserCount()).thenReturn(30L);
        when(adminDataCenterMapper.totalRoleCount()).thenReturn(40L);
        when(adminDataCenterMapper.totalDictDataCount()).thenReturn(150L);
        when(adminDataCenterMapper.totalComQueryCount()).thenReturn(20L);
        when(adminDataCenterMapper.totalRolePermissionCount()).thenReturn(120L);
        when(adminDataCenterMapper.totalUserRoleCount()).thenReturn(70L);

        DataVo result = service.queryData();

        assertNotNull(result);
        assertEquals(1, result.getUser().size());
        assertEquals(10L, result.getUser().get(0).getCount());
        assertEquals(100L, result.getTotalUser());
        assertEquals(50L, result.getTotalNotice());
        assertEquals(200L, result.getTotalOperLog());
        assertEquals(80L, result.getTotalPermission());
        assertEquals(30L, result.getTotalFrontUser());
        assertEquals(40L, result.getTotalRole());
        assertEquals(150L, result.getTotalDictData());
        assertEquals(20L, result.getTotalComQuery());
    }

    @Test
    @DisplayName("queryData 应调用所有 mapper 方法")
    void queryDataCallsAllMapperMethods() {
        when(adminDataCenterMapper.userCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.noticeCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.operLogCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.permissionCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.frontUserCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.roleCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.dictDataCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.comQueryCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.rolePermissionCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.userRoleCount()).thenReturn(emptyList);
        when(adminDataCenterMapper.totalUserCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalNoticeCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalOperLogCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalPermissionCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalFrontUserCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalRoleCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalDictDataCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalComQueryCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalRolePermissionCount()).thenReturn(0L);
        when(adminDataCenterMapper.totalUserRoleCount()).thenReturn(0L);

        service.queryData();

        verify(adminDataCenterMapper).userCount();
        verify(adminDataCenterMapper).noticeCount();
        verify(adminDataCenterMapper).operLogCount();
        verify(adminDataCenterMapper).permissionCount();
        verify(adminDataCenterMapper).frontUserCount();
        verify(adminDataCenterMapper).roleCount();
        verify(adminDataCenterMapper).totalUserCount();
        verify(adminDataCenterMapper).totalNoticeCount();
    }
}
