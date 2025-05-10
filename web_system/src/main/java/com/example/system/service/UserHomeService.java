package com.example.system.service;

import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserHomeService {
    /**
     * 查询通知
     *
     * @return
     */
    List<NoticeVo> queryNotice(Notice noticeVo);
}
