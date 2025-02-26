package com.example.system.service;

import com.example.system.domain.vo.NoticeVo;
import com.github.pagehelper.PageInfo;

public interface UserHomeService {
    /**
     * 查询通知
     *
     * @param noticeVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<NoticeVo> queryNotice(NoticeVo noticeVo, Integer currentPage, Integer pageSize);
}
