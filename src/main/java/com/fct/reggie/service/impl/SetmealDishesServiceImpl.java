package com.fct.reggie.service.impl;

import com.fct.reggie.dao.SetmealDishesMapper;
import com.fct.reggie.pojo.SetmealDish;
import com.fct.reggie.service.SetmealDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishesServiceImpl implements SetmealDishesService {
    @Autowired
    private SetmealDishesMapper setmealDishesMapper;

    @Override
    public Integer insert(SetmealDish setmealDish) {
        return setmealDishesMapper.insert(setmealDish);

    }

    @Override
    public void remove(String id) {
        setmealDishesMapper.remove(id);
    }
}
