package com.fct.reggie.service;

import com.fct.reggie.pojo.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart selectDishAndSetmeal(ShoppingCart shoppingCart);

    Integer updateNumber(ShoppingCart shoppingCart);

    Integer insert(ShoppingCart shoppingCart);

    List<ShoppingCart> list(Long userId);

    Integer updateNumberSub(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    Integer deleteByUserId(Long userId);
}
