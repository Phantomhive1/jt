package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSONPController {

    @RequestMapping("/web/testJSONP")
    public JSONPObject jsonp(String callback) {
        //准备返回的数据
        User user = new User();
        user.setId(100L).setPassword("我是密码");
        return new JSONPObject(callback, user);
    }
}
