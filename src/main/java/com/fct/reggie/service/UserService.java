package com.fct.reggie.service;

import com.fct.reggie.pojo.User;

public interface UserService {
    User getMessage(String phone);

    Integer insert(User user);

    //根据用户id获取信息
    User getById(Long userId);
}
