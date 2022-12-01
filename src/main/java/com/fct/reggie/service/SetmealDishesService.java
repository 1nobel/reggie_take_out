package com.fct.reggie.service;

import com.fct.reggie.pojo.SetmealDish;

import java.util.List;

public interface SetmealDishesService {

    Integer insert(SetmealDish setmealDish);

    void remove(String id);
}
