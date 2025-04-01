package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.system.mapper")
public class WebManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebManageApplication.class, args);
    }
}
