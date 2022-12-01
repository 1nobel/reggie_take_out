package com.fct.reggie.service;

import com.fct.reggie.dto.SetmealDto;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    Integer selectByCategory(Long id);

    void insert(SetmealDto setmealDto);

    Page<Setmeal> page(Integer page, Integer pageSize, String name);

    void remove(List<Long> ids);

    Integer getStatus(String id);

    List<Setmeal> getList(Long categoryId, Integer status);

}
