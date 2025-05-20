package com.example.springboot;


import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

public class TestApi {

    @Test
    public void test() {
        // 校验当前账号是否以 User 身份进行登录
        String s = DigestUtils.md5DigestAsHex("admin123".getBytes());
        System.out.println(s);
    }
}
