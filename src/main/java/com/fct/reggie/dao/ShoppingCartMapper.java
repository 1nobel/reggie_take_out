package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    //查看当前菜品是否存在购物车中
    ShoppingCart selectDishAndSetmeal(ShoppingCart shoppingCart);

    //更新数量
    Integer updateNumber(ShoppingCart shoppingCart);
    Integer updateNumberSub(ShoppingCart shoppingCart);
    //将菜品或套餐添加到购物车
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer insert(ShoppingCart shoppingCart);

    //查看购物车
    List<ShoppingCart> list(Long userId);

    //如果number<0 将number改为0
    Integer update(ShoppingCart shoppingCart);

    //清空购物车
    Integer deleteByUserId(Long userId);
}
