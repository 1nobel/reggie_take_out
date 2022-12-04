package com.fct.reggie.service.impl;

import com.fct.reggie.common.CustomException;
import com.fct.reggie.dao.DishMapper;
import com.fct.reggie.dto.DishDto;
import com.fct.reggie.pojo.Dish;
import com.fct.reggie.pojo.DishFlavor;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.service.DishFlavorService;
import com.fct.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public Integer selectByCategory(Long id) {
        return dishMapper.selectByCategory(id);
    }

    /**
     * 新增菜品同时保存对应的口味数据
     *
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public Integer insert(DishDto dishDto) {

        Integer integer = dishMapper.insertDish(dishDto);
        //查询菜品id
        Long dishId = selectIdByName(dishDto.getName());

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
            dishFlavorService.insert(flavor);
        }
        return integer;
    }

    @Override
    public Long selectIdByName(String name) {
        return dishMapper.selectIdByName(name);
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Dish> page(Integer page, Integer pageSize, String name) {
        //如果输入框中name为null则自动转为“”串
        if (name == null) {
            name = "";
        }

        Page<Dish> pages = new Page<>();

        int a = (page - 1) * pageSize;
        //调用mapper查询页面信息
        List<Dish> page1 = dishMapper.page(a, pageSize, name);
        pages.setRecords(page1);
        //调用mapper查询数据数目
        Long count = dishMapper.selectCount(name);
        pages.setTotal(count);

        return pages;
    }

    //获取DishDto的信息
    @Override
    public DishDto get(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = dishMapper.get(id);
        if(dish == null){
            throw new CustomException("未找到相关菜品信息");
        }
        List<DishFlavor> dishFlavors = dishFlavorService.get(id);

        //对象拷贝
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(dishFlavors);
        return dishDto;
    }

    @Transactional
    @Override
    public void update(DishDto dishDto) {
        //更新菜品信息
        dishMapper.update(dishDto);

        //删除口味信息
        dishFlavorService.delete(dishDto.getId());

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors){
            dishFlavorService.insert(dishFlavor);
        }

    }

    @Override
    public List<Dish> getList(Long categoryId) {
        return dishMapper.getList(categoryId);
    }

    @Override
    public List<Dish> getList2(Long categoryId, Integer status) {
        return dishMapper.getList2(categoryId, status);
    }
}
