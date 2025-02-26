package com.example.controller.user;

import com.example.common.entity.Result;
import com.example.system.domain.vo.NoticeVo;
import com.example.system.service.UserHomeService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户主页相关接口")
@RestController
@RequestMapping("/user/home")
@Slf4j
public class UserHomeController {
    private final UserHomeService userHomeService;

    public UserHomeController(UserHomeService userHomeService) {
        this.userHomeService = userHomeService;
    }

    /**
     * 查询通知
     *
     * @param noticeVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询通知")
    @GetMapping
    public Result<PageInfo<NoticeVo>> queryNotice(NoticeVo noticeVo,
                                                  @RequestParam(defaultValue = "1") Integer currentPage,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询{}，{}，{}", noticeVo, currentPage, pageSize);
        PageInfo<NoticeVo> page = userHomeService.queryNotice(noticeVo, currentPage, pageSize);
        return Result.success(page);
    }
}
