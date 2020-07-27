package com.jt.web.controller;

import com.jt.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {
    @RequestMapping("/web/testCors")
    public User testUser() {
        System.out.println("CorsController.testUser");
        return new User().setId(101L).setPassword("我是Cors返回值");
    }
}
