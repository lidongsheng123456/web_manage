package com.example.controller.user;

import com.example.common.entity.Result;
import com.example.system.domain.Notice;
import com.example.system.service.UserHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台数据显示类，不拦截
 */
@Tag(name = "用户主页相关接口")
@RestController
@RequestMapping("/user/home")
@RequiredArgsConstructor
public class UserHomeController {
    private final UserHomeService userHomeService;

    /**
     * 查询通知
     *
     * @return
     */
    @Operation(summary = "查询通知")
    @GetMapping("/notice")
    public Result queryNotice(Notice noticeVo) {
        List<Object> list = userHomeService.queryNotice(noticeVo);
        return Result.success(list);
    }
}
