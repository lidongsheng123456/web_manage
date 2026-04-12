package com.example.service;

import com.example.system.domain.Notice;
import com.example.system.mapper.AdminNoticeMapper;
import com.example.system.service.impl.AdminNoticeServiceImpl;
import com.github.pagehelper.PageInfo;
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
 * AdminNoticeServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminNoticeServiceImpl 通知管理服务测试")
class AdminNoticeServiceImplTest {

    @Mock
    private AdminNoticeMapper adminNoticeMapper;

    private AdminNoticeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AdminNoticeServiceImpl(adminNoticeMapper);
    }

    @Test
    @DisplayName("queryNotice 标题模糊查询")
    void queryNoticeByTitle() {
        Notice notice = new Notice();
        notice.setNoticeTitle("测试");

        when(adminNoticeMapper.selectList(any())).thenReturn(List.of());

        PageInfo<Notice> result = service.queryNotice(notice, 1, 10);

        assertNotNull(result);
    }

    @Test
    @DisplayName("queryNotice 内容模糊查询")
    void queryNoticeByContent() {
        Notice notice = new Notice();
        notice.setNoticeContent("内容");

        when(adminNoticeMapper.selectList(any())).thenReturn(List.of());

        PageInfo<Notice> result = service.queryNotice(notice, 1, 10);

        assertNotNull(result);
    }

    @Test
    @DisplayName("queryNotice 无条件查询")
    void queryNoticeNoCondition() {
        Notice notice = new Notice();

        when(adminNoticeMapper.selectList(any())).thenReturn(List.of());

        PageInfo<Notice> result = service.queryNotice(notice, 1, 10);

        assertNotNull(result);
    }
}
