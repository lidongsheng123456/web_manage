package com.example.controller.user;

import com.example.system.service.UserOperationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台数据操作类，拦截
 */
@Tag(name = "用户主页操作相关接口")
@RestController
@RequestMapping("/user/oper")
@RequiredArgsConstructor
public class UserOperationController {
    private final UserOperationService userOperationService;
}
