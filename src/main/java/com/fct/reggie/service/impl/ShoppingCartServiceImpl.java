package com.fct.reggie.service.impl;

import com.fct.reggie.dao.ShoppingCartMapper;
import com.fct.reggie.pojo.ShoppingCart;
import com.fct.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart selectDishAndSetmeal(ShoppingCart shoppingCart) {
        return shoppingCartMapper.selectDishAndSetmeal(shoppingCart);
    }

    @Override
    public Integer updateNumber(ShoppingCart shoppingCart) {
        return shoppingCartMapper.updateNumber(shoppingCart);
    }

    @Override
    public Integer insert(ShoppingCart shoppingCart) {
        return shoppingCartMapper.insert(shoppingCart);
    }

    @Override
    public List<ShoppingCart> list(Long userId) {
        return shoppingCartMapper.list(userId);
    }

    @Override
    public Integer updateNumberSub(ShoppingCart shoppingCart) {
        return shoppingCartMapper.updateNumberSub(shoppingCart);
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartMapper.update(shoppingCart);
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return shoppingCartMapper.deleteByUserId(userId);
    }
}
