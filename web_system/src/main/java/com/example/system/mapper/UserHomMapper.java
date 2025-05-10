package com.example.system.mapper;

import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;

import java.util.List;

public interface UserHomMapper {
    /**
     * 查询通知
     *
     * @return
     */
    List<NoticeVo> queryNotice(Notice noticeVo);
}
