package com.fct.reggie.service.impl;

import com.fct.reggie.dao.UserMapper;
import com.fct.reggie.pojo.User;
import com.fct.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getMessage(String phone) {
        return userMapper.getMessage(phone);
    }

    @Override
    public Integer insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User getById(Long userId) {
        return userMapper.getById(userId);
    }
}
