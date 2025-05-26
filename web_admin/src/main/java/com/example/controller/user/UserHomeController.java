package com.example.controller.user;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.entity.Result;
import com.example.common.redis.RedisCache;
import com.example.system.domain.Notice;
import com.example.system.domain.vo.NoticeVo;
import com.example.system.service.UserHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 前台数据显示类，不拦截
 */
@Tag(name = "用户主页相关接口")
@RestController
@RequestMapping("/user/home")
@RequiredArgsConstructor
public class UserHomeController {
    private final UserHomeService userHomeService;

    private final RedisCache redisCache;

    /**
     * 查询通知
     *
     * @return
     */
    @Operation(summary = "查询通知")
    @GetMapping("/notice")
    public Result queryNotice(Notice noticeVo) {
        // 缓存有效返回缓存
        List<NoticeVo> cacheObject = redisCache.getCacheObject("notice-list");
        if (ObjectUtil.isNotEmpty(cacheObject)) {
            return Result.success(cacheObject);
        }

        // 缓存无效返回数据库
        List<NoticeVo> list = userHomeService.queryNotice(noticeVo);
        // 1分钟后过期，下次查询时会重新从数据库加载最新数据。
        redisCache.setCacheObject("notice-list", list, 1, TimeUnit.MINUTES);
        return Result.success(list);
    }
}
