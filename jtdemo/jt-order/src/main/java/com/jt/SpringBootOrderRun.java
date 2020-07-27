package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.mapper")
public class SpringBootOrderRun {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootOrderRun.class, args);

    }
}
