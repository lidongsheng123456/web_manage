package com.example.system.service;

import com.example.system.domain.Notice;

import java.util.List;

public interface UserHomeService {
    /**
     * 查询通知
     *
     * @return
     */
    List<Object> queryNotice(Notice noticeVo);
}
