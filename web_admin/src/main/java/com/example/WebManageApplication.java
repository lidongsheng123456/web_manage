package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.system.mapper")
public class WebManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebManageApplication.class, args);
        System.out.println("(‿)ﾉﾞ  东神脚手架启动成功   ლ(ڡ`ლ)");
    }
}
