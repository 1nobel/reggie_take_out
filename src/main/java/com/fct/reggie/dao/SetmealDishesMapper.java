package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.pojo.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishesMapper {

    @AutoFill(type = AutoFillConstant.INSERT)
    Integer insert(SetmealDish setmealDish);

    Integer remove(String id );
}
