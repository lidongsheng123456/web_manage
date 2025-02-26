package com.example.system.service;

import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminNoticeService {
    /**
     * 新增通知
     *
     * @param notice
     */
    void addNotice(Notice notice);

    /**
     * 删除通知
     *
     * @param id
     */
    void deleteNotice(Long id);

    /**
     * 批量删除通知
     *
     * @param ids
     */
    void batchDeleteNotice(List<Long> ids);

    /**
     * 修改通知
     *
     * @param notice
     */
    void updateNotice(Notice notice);

    /**
     * 查询通知
     *
     * @param noticeVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageInfo<NoticeVo> queryNotice(NoticeVo noticeVo, Integer currentPage, Integer pageSize);

    /**
     * 查询全部通知
     *
     * @return
     */
    List<Notice> queryAllNotice();

    /**
     * 根据id查询通知
     *
     * @param id
     * @return
     */
    Notice queryNoticeById(Long id);
}
