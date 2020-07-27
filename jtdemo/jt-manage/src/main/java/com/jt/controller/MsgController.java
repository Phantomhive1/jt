package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/getPort")
    public String getPort() {
        return "当前服务器端口号为： "+port;
    }
}
