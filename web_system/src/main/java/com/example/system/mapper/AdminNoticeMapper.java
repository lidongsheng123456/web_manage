package com.example.system.mapper;

import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminNoticeMapper {
    /**
     * 新增通知
     *
     * @param notice
     * @return
     */
    int addNotice(Notice notice);

    /**
     * 删除通知
     *
     * @param id
     * @return
     */
    int deleteNotice(Long id);

    /**
     * 根据用户id查询通知
     *
     * @param id
     */
    void deleteNoticeByUserId(Long id);

    /**
     * 根据用户id批量删除通知
     *
     * @param ids
     */
    void batchDeleteNoticeByUserIds(List<Long> ids);

    /**
     * 批量删除通知
     *
     * @param ids
     * @return
     */
    int batchDeleteNotice(List<Long> ids);

    /**
     * 修改通知
     *
     * @param notice
     * @return
     */
    int updateNotice(Notice notice);

    /**
     * 查询通知
     *
     * @param noticeVo
     * @return
     */
    List<NoticeVo> queryNotice(NoticeVo noticeVo);

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
