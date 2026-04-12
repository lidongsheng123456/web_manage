package com.example.controller;

import com.example.common.entity.Result;
import com.example.controller.admin.AdminDataCenterController;
import com.example.system.domain.vo.DataCountTypeVo;
import com.example.system.domain.vo.DataVo;
import com.example.system.service.AdminDataCenterService;
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
 * AdminDataCenterController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminDataCenterController 数据中心接口测试")
class AdminDataCenterControllerTest {

    @Mock
    private AdminDataCenterService adminDataCenterService;

    private AdminDataCenterController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminDataCenterController(adminDataCenterService);
    }

    @Test
    @DisplayName("queryData 成功返回数据")
    void queryDataSuccess() {
        List<DataCountTypeVo> userList = List.of(new DataCountTypeVo("Mon", 10L));
        List<DataCountTypeVo> noticeList = List.of(new DataCountTypeVo("Mon", 5L));
        DataVo dataVo = DataVo.builder()
                .user(userList)
                .notice(noticeList)
                .totalUser(100L)
                .totalNotice(50L)
                .build();
        when(adminDataCenterService.queryData()).thenReturn(dataVo);

        Result<DataVo> result = controller.queryData();

        assertEquals(200, result.getCode());
        assertEquals(1, result.getData().getUser().size());
        assertEquals(100L, result.getData().getTotalUser());
    }

    @Test
    @DisplayName("queryData 调用服务层")
    void queryDataCallsService() {
        DataVo dataVo = DataVo.builder().build();
        when(adminDataCenterService.queryData()).thenReturn(dataVo);

        controller.queryData();

        verify(adminDataCenterService).queryData();
    }
}
