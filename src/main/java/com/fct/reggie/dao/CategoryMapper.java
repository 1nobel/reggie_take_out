package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
//  插入分类
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer insert(Category category);

//    分页查询
    List<Category> page(@Param("a") Integer a, @Param("pageSize") Integer pageSize);

//    查询数据数量
    Integer selectCount();

//    根据id删除菜品分类
    Integer deleteById(Long id);

//    修改菜品分类
    @AutoFill(type = AutoFillConstant.UPDATE)
    Integer update(Category category);

//    查询菜品分类
    List<Category> selectName(Integer type);

//    查询全部分类
    List<Category> selectAll();

//    根据id查询
    String selectById(Long id);
}
