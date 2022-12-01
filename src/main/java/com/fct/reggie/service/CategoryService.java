package com.fct.reggie.service;

import com.fct.reggie.pojo.Category;

import java.util.List;

public interface CategoryService {

    boolean insert(Category category);

    List<Category> page(Integer a, Integer pageSize);

    Integer selectCount();

    Integer deleteById(Long id);

    Integer update(Category category);

    List<Category> selectName(Integer type);

    String selectById(Long id);

    List<Category> selectAll();
}
