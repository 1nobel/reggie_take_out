package com.fct.reggie.dao;


import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.pojo.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    //添加口味信息
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer insert(DishFlavor dishFlavor);

    //根据dish_id查询口味信息
    List<DishFlavor> get(@Param("dishId")Long id);

    //更新口味信息
    @AutoFill(type = AutoFillConstant.UPDATE)
    Integer update(DishFlavor dishFlavor);

    //删除口味信息
    Integer delete(Long dishId);
}
