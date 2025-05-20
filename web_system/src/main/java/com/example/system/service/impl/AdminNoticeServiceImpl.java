package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.domain.Notice;
import com.example.system.mapper.AdminNoticeMapper;
import com.example.system.service.AdminNoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNoticeServiceImpl extends ServiceImpl<AdminNoticeMapper, Notice> implements AdminNoticeService {
    private final AdminNoticeMapper adminNoticeMapper;

    @Override
    public PageInfo<Notice> queryNotice(Notice notice, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper<Notice>()
                .like(notice.getNoticeTitle() != null && !notice.getNoticeTitle().isEmpty(), Notice::getNoticeTitle, notice.getNoticeTitle())
                .like(notice.getNoticeContent() != null && !notice.getNoticeContent().isEmpty(), Notice::getNoticeContent, notice.getNoticeContent());

        List<Notice> notices = adminNoticeMapper.selectList(lambdaQueryWrapper);
        return PageInfo.of(notices);
    }
}
