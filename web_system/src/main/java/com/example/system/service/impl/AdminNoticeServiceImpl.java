package com.example.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.annotation.AutoFill;
import com.example.common.enums.BusinessType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import com.example.system.mapper.AdminNoticeMapper;
import com.example.system.service.AdminNoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminNoticeServiceImpl implements AdminNoticeService {
    private final AdminNoticeMapper adminNoticeMapper;

    public AdminNoticeServiceImpl(AdminNoticeMapper adminNoticeMapper) {
        this.adminNoticeMapper = adminNoticeMapper;
    }

    /**
     * 新增通知
     *
     * @param notice
     */
    @Override
    @AutoFill(BusinessType.INSERT)
    public void addNotice(Notice notice) {
        validateField(notice);

        notice.setCreateUserId(StpUtil.getLoginIdAsLong());

        isSuccess(adminNoticeMapper.addNotice(notice));
    }

    /**
     * 删除通知
     *
     * @param id
     */
    @Override
    public void deleteNotice(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminNoticeMapper.deleteNotice(id));
    }

    /**
     * 批量删除通知
     *
     * @param ids
     */
    @Override
    public void batchDeleteNotice(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        isSuccess(adminNoticeMapper.batchDeleteNotice(ids));
    }

    /**
     * 修改通知
     *
     * @param notice
     */
    @Override
    @AutoFill(BusinessType.UPDATE)
    public void updateNotice(Notice notice) {
        validateField(notice);

        isSuccess(adminNoticeMapper.updateNotice(notice));
    }

    /**
     * 查询通知
     *
     * @param noticeVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<NoticeVo> queryNotice(NoticeVo noticeVo, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<NoticeVo> list = adminNoticeMapper.queryNotice(noticeVo);
        return PageInfo.of(list);
    }

    /**
     * 查询全部通知
     *
     * @return
     */
    @Override
    public List<Notice> queryAllNotice() {
        return adminNoticeMapper.queryAllNotice();
    }

    /**
     * 根据id查询通知
     *
     * @param id
     * @return
     */
    @Override
    public Notice queryNoticeById(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        return adminNoticeMapper.queryNoticeById(id);
    }

    /**
     * 是否成功
     *
     * @param i
     */
    public void isSuccess(Integer i) {
        if (i == 0) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 验证字段
     *
     * @param notice
     */
    public void validateField(Notice notice) {
        if (ObjectUtil.isEmpty(notice.getNoticeTitle()) || ObjectUtil.isEmpty(notice.getNoticeContent())) {
            throw new BusinessException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
    }
}
