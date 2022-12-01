package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.dto.DishDto;
import com.fct.reggie.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {

    Integer selectByCategory(@Param("categoryId")Long id);

    //添加菜品分类
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer insertDish(DishDto dishDto);

    //根据菜品名称查找id
    Long selectIdByName(String name);

    //分页查询数据
    List<Dish> page(@Param("a") Integer a,@Param("pageSize") Integer pageSize,@Param("name") String name);

    //查询数据数量
    Long selectCount(String name);

    //根据id查询菜品信息
    Dish get(Long id);

    //修改菜品信息
    @AutoFill(type = AutoFillConstant.UPDATE)
    Integer update(DishDto dishDto);

    //根据菜品类别查询菜品信息
    List<Dish> getList(Long categoryId);

    //根据菜品类别和在售状态查询菜品信息
    List<Dish> getList2(Long categoryId,Integer status);



}
