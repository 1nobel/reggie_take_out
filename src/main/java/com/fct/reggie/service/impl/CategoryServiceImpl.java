package com.fct.reggie.service.impl;

import com.fct.reggie.common.CustomException;
import com.fct.reggie.dao.CategoryMapper;
import com.fct.reggie.pojo.Category;
import com.fct.reggie.service.CategoryService;
import com.fct.reggie.service.DishService;
import com.fct.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
//自动装配
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

//
    @Override
    public boolean insert(Category category1) {
        Integer flag = categoryMapper.insert(category1);
        if(flag == 1 ){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<Category> page(Integer page, Integer pageSize) {
        Integer a =(page-1)*pageSize;
        return categoryMapper.page(a,pageSize);
    }

    @Override
    public Integer selectCount() {
        return categoryMapper.selectCount();
    }

    @Override
    public Integer deleteById(Long id) {
        Integer count1 = dishService.selectByCategory(id);

        if(count1 > 0){
            throw new CustomException("删除失败，该菜品分类中有菜品！！！");
        }

        Integer count2 = setmealService.selectByCategory(id);

        if(count2 > 0){
            throw new CustomException("删除失败，该菜品分类中已关联套餐！！！");

        }
        return categoryMapper.deleteById(id);
    }

    @Override
    public Integer update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    public List<Category> selectName(Integer type) {
        return categoryMapper.selectName(type);
    }

    @Override
    public String selectById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }
}
