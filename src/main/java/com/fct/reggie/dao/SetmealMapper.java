package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.dto.SetmealDto;
import com.fct.reggie.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealMapper {
    //查询菜品分类编号对应的id
    Integer selectByCategory(@Param("categoryId")Long id);

    //添加套餐信息
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer insert(SetmealDto setmealDto);

    Long selectIdByName(String name);

    List<Setmeal> page(Integer a, Integer pageSize, String name);

    Long selectCount(String name);

    Integer remove(String id);

    //查询售卖状态
    Integer getStatus(String id);

    //查询套餐信息
    List<Setmeal> getList(Long categoryId, Integer status);
}
