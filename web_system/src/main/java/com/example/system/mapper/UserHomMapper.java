package com.example.system.mapper;

import com.example.system.domain.Notice;

import java.util.List;

public interface UserHomMapper {
    /**
     * 查询通知
     *
     * @return
     */
    List<String> queryNotice(Notice notice);

    /**
     * 根据通知ids查询通知
     *
     * @param noHitIdList
     * @return
     */
    List<Notice> batchQueryNotice(List<String> noHitIdList);
}
