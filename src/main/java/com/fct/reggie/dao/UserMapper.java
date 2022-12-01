package com.fct.reggie.dao;

import com.fct.reggie.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getMessage(String phone);

    Integer insert(User user);

    //根据用户id获取信息
    User getById(Long userId);

}
