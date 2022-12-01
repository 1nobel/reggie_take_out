package com.fct.reggie.service.impl;

import com.fct.reggie.dao.DishFlavorMapper;
import com.fct.reggie.pojo.DishFlavor;
import com.fct.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public Integer insert(DishFlavor dishFlavor) {
        return dishFlavorMapper.insert(dishFlavor);
    }

    @Override
    public List<DishFlavor> get(Long id) {
        return dishFlavorMapper.get(id);
    }

    @Override
    public Integer update(DishFlavor dishFlavor) {
        return dishFlavorMapper.update(dishFlavor);
    }

    @Override
    public void delete(Long dishId) {
        dishFlavorMapper.delete(dishId);
    }


}
