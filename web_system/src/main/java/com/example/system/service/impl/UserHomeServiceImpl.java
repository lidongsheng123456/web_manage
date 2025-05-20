package com.example.system.service.impl;

import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import com.example.system.mapper.UserHomMapper;
import com.example.system.service.UserHomeService;
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
     * @return
     */
    @Override
    public List<NoticeVo> queryNotice(Notice noticeVo) {
        return userHomMapper.queryNotice(noticeVo);
    }
}
