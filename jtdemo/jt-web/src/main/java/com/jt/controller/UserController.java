package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JedisCluster jedisCluster;

    @Reference(check = false)
    private DubboUserService userService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user) {
        userService.saveUser(user);
        return SysResult.success();
    }

    @RequestMapping("/doLogin")
    public SysResult doLogin(User user, HttpServletResponse response) {
        String ticket = userService.doLogin(user);
        if (StringUtils.isEmpty(ticket)) {
            return SysResult.fail();
        }

        Cookie cookie = new Cookie("JT_TICKET", ticket);
        cookie.setDomain("jt.com");
        cookie.setPath("/");
        cookie.setMaxAge(7*24*3600);

        response.addCookie(cookie);
        return SysResult.success();

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("JT_TICKET".equalsIgnoreCase(cookie.getName())) {
                    String ticket = cookie.getValue();
                    jedisCluster.del(ticket);

                    cookie.setDomain("jt.com");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "redirect:/";

    }

}
