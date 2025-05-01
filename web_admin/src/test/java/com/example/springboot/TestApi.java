package com.example.springboot;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestApi {

    @Test
    public void test() {
        // 校验当前账号是否以 User 身份进行登录
//        StpUserUtil.getSession();    // 获取当前 User 账号的 Access-Session 对象
//        StpUserUtil.checkPermission("xx");    // 校验当前登录的 user 账号是否具有 xx 权限
    }
}
