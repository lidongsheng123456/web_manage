package com.example.system.mapper;

import com.example.system.domain.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserHomMapper {
    /**
     * 查询通知
     *
     * @param noticeVo
     * @return
     */
    List<NoticeVo> queryNotice(NoticeVo noticeVo);
}
