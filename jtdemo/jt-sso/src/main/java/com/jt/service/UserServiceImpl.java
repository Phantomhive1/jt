package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean checkUser(String param, Integer type) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "username");
        map.put(2, "phone");
        map.put(3, "email");
        String column = map.get(type);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, param);
        int count = userMapper.selectCount(queryWrapper);
        return count>0;
    }
}
