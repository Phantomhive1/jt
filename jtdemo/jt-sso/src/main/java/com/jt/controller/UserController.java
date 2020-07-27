package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private UserService userService;

    @GetMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable("param") String param, @PathVariable("type") Integer type, String callback) {
        boolean flag = userService.checkUser(param, type);
        SysResult sysResult = SysResult.success(flag);
        return new JSONPObject(callback, sysResult);
    }

    @RequestMapping("/query/{ticket}")
    public JSONPObject findUserByTicket(String callback, @PathVariable String ticket) {
        if (jedisCluster.exists(ticket)) {
            String data = jedisCluster.get(ticket);
            SysResult sysResult = SysResult.success(data);
            return new JSONPObject(callback, sysResult);
        } else {
            SysResult sysResult = SysResult.fail();
            return new JSONPObject(callback, sysResult);
        }
    }

}
