package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.Notice;
import com.github.pagehelper.PageInfo;

public interface AdminNoticeService extends IService<Notice> {
    PageInfo<Notice> queryNotice(Notice notice, Integer currentPage, Integer pageSize);
}
