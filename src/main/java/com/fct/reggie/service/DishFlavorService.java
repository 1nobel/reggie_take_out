package com.fct.reggie.service;

import com.fct.reggie.pojo.DishFlavor;

import java.util.List;

public interface DishFlavorService {

    Integer insert(DishFlavor dishFlavor);

    List<DishFlavor> get(Long id);

    Integer update(DishFlavor dishFlavor);

    void delete(Long dishId);
}
