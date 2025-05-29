package com.example.system.service;

import com.example.system.domain.FrontUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.Notice;
import com.example.system.domain.vo.FrontUserVo;
import com.github.pagehelper.PageInfo;

public interface AdminFrontUserService extends IService<FrontUser> {
    PageInfo<FrontUserVo> queryFrontUser(FrontUser frontUser, Integer currentPage, Integer pageSize);
}
