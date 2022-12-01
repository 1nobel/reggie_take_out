package com.fct.reggie.service;


import com.fct.reggie.dto.DishDto;
import com.fct.reggie.pojo.Dish;
import com.fct.reggie.pojo.Page;

import java.util.List;

public interface DishService {

    Integer selectByCategory(Long id);

    Integer insert(DishDto dishDto);

    Long selectIdByName(String name);

    Page<Dish> page(Integer page, Integer pageSize, String name);

    DishDto get(Long id);

    void update(DishDto dishDto);

    List<Dish> getList(Long categoryId);

    List<Dish> getList2(Long categoryId, Integer status);
}
