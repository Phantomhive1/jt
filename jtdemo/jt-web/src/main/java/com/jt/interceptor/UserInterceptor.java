package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if ("JT_TICKET".equals(cookie.getName())) {
                    String ticket = cookie.getValue();
                    if (jedisCluster.exists(ticket)) {

                        String userJSON = jedisCluster.get(ticket);
                        User user = ObjectMapperUtil.toObject(userJSON, User.class);
                        request.setAttribute("JT_USER", user);
                        return true;
                    }
                }
            }
        }

        response.sendRedirect("/user/login.html");
        return false;
    }
}
