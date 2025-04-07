package com.example.system.service.impl;

import com.example.system.domain.vo.NoticeVo;
import com.example.system.mapper.UserHomMapper;
import com.example.system.service.UserHomeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHomeServiceImpl implements UserHomeService {

    private final UserHomMapper userHomMapper;

    /**
     * 查询通知
     *
     * @param noticeVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<NoticeVo> queryNotice(NoticeVo noticeVo, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<NoticeVo> list = userHomMapper.queryNotice(noticeVo);
        return PageInfo.of(list);
    }
}
