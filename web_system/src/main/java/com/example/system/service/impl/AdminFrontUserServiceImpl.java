package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.domain.FrontUser;
import com.example.system.domain.vo.FrontUserVo;
import com.example.system.mapper.AdminFrontUserMapper;
import com.example.system.service.AdminFrontUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFrontUserServiceImpl extends ServiceImpl<AdminFrontUserMapper, FrontUser> implements AdminFrontUserService {
    private final AdminFrontUserMapper adminFrontUserMapper;

    @Override
    public PageInfo<FrontUserVo> queryFrontUser(FrontUser frontUser, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<FrontUser> lambdaQueryWrapper = new LambdaQueryWrapper<FrontUser>()
                .like(frontUser.getUsername() != null && !frontUser.getUsername().isEmpty(), FrontUser::getUsername, frontUser.getUsername());

        List<FrontUser> frontUsers = adminFrontUserMapper.selectList(lambdaQueryWrapper);
        List<FrontUserVo> frontUserVos = new ArrayList<>(frontUsers.size());
        for (FrontUser user : frontUsers) {
            FrontUserVo frontUserVo = new FrontUserVo();
            BeanUtils.copyProperties(user, frontUserVo);
            frontUserVos.add(frontUserVo);
        }

        return PageInfo.of(frontUserVos);
    }
}
