package com.fct.reggie.controller;

import com.fct.reggie.common.BaseContext;
import com.fct.reggie.common.Result;
import com.fct.reggie.pojo.ShoppingCart;
import com.fct.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    //同一个菜品点了两份，只需要修改菜品或套餐数量，所以添加之前需要查看菜品是否存在，如果存在就在原来基础上加一，如果不存在则添加到购物车中
    //数量默认为1
    @PostMapping("/add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("{}", shoppingCart);
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //如果获取到的数量为null，则将数量设置为1

        //查询购物车信息
        ShoppingCart shoppingCartOld = shoppingCartService.selectDishAndSetmeal(shoppingCart);
        //查看菜品是否存在
        if (shoppingCartOld != null) {
            //判断口味是否一致
//            if (shoppingCart.getDishFlavor().equals(shoppingCartOld.getDishFlavor())){

            shoppingCartService.updateNumber(shoppingCartOld);
//            }
        } else {
            shoppingCart.setNumber(1);
            shoppingCartService.insert(shoppingCart);
        }
        shoppingCart = shoppingCartService.selectDishAndSetmeal(shoppingCart);

        return Result.success(1, shoppingCart);
    }


    /**
     * 减少购物车商品数量
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public Result<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("{}", shoppingCart);
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //查询购物车信息
        shoppingCart = shoppingCartService.selectDishAndSetmeal(shoppingCart);
        shoppingCartService.updateNumberSub(shoppingCart);


        //如果商品数量<=0或者为null，则修改数量为0
        if(shoppingCart.getNumber() <= 0 || shoppingCart.getNumber() == null ){
            shoppingCartService.update(shoppingCart);
        }
        shoppingCart = shoppingCartService.selectDishAndSetmeal(shoppingCart);
        return Result.success(1, shoppingCart);
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        //获取当前用户的id
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> list = shoppingCartService.list(userId);
        return Result.success(1, list);
    }

    @DeleteMapping("/clean")
    public Result<String> delete(){
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.deleteByUserId(userId);
        return Result.success(1,"清空购物车成功！！！");
    }
}
