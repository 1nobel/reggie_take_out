package com.fct.reggie.service.impl;


import com.fct.reggie.common.CustomException;
import com.fct.reggie.dao.SetmealMapper;
import com.fct.reggie.dto.SetmealDto;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.pojo.Setmeal;
import com.fct.reggie.pojo.SetmealDish;
import com.fct.reggie.service.SetmealDishesService;
import com.fct.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishesService setmealDishesService;

    @Override
    public Integer selectByCategory(Long id) {
        return setmealMapper.selectByCategory(id);
    }

    @Override
    public void insert(SetmealDto setmealDto) {
        setmealMapper.insert(setmealDto);
        Long setmealId = setmealMapper.selectIdByName(setmealDto.getName());
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
            setmealDishesService.insert(setmealDish);
        }
    }

    @Override
    public Page<Setmeal> page(Integer page, Integer pageSize, String name) {
        if (name == null) {
            name = "";
        }

        Page<Setmeal> page1 = new Page<>();
        Integer a = (page - 1) * pageSize;
        List<Setmeal> setmeals = setmealMapper.page(a, pageSize, name);
        page1.setRecords(setmeals);
        Long count = setmealMapper.selectCount(name);
        page1.setTotal(count);

        return page1;
    }

    /**
     * 删除套餐
     * @param ids
     */
    @Transactional
    @Override
    public void remove(List<Long> ids) {


        for (Long a : ids){
            //将long型转为string
            String id = a +"";
            //查询套餐状态
            Integer status = setmealMapper.getStatus(id);
            if(status == 1){
                throw new CustomException("该产品正在售卖，不能删除");
            }

            //删除套餐表
            setmealMapper.remove(id);

            //删除套餐关系表
            setmealDishesService.remove(id);
        }
    }

    @Override
    public Integer getStatus(String id) {
        return setmealMapper.getStatus(id);
    }

    @Override
    public List<Setmeal> getList(Long categoryId, Integer status) {
        return setmealMapper.getList(categoryId, status);
    }
}


