package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.UUID;

@Service
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void saveUser(User user) {
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password).setEmail(user.getEmail()).setPhone(user.getPhone()).setCreated(new Date()).setUpdated(user.getCreated());
        userMapper.insert(user);
    }

    @Override
    public String doLogin(User user) {
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User user1 = userMapper.selectOne(queryWrapper);

        if (user1 == null) {
            return null;
        }
        String ticket = UUID.randomUUID().toString().replace("-", "");
        user1.setPassword("YouGuess");
        String json = ObjectMapperUtil.toJSON(user1);
        jedisCluster.setex(ticket, 7*24*3600, json);

        return ticket;
    }
}
