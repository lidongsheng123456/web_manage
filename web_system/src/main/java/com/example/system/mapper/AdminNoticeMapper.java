package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.domain.Notice;

import java.util.List;

public interface AdminNoticeMapper extends BaseMapper<Notice> {

    void batchDeleteNoticeByUserIds(List<Long> ids);
}
