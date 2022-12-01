package com.fct.reggie.service.impl;

import com.fct.reggie.common.BaseContext;
import com.fct.reggie.common.CustomException;
import com.fct.reggie.pojo.*;
import com.fct.reggie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;
    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    public void submit(Orders orders){
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();
        //查询当前用户购物车数据
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(userId);
        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }
        //查询用户数据
        User user = userService.getById(userId);
        //查询地址数据
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if(addressBook == null){
            throw new CustomException("地址信息有误，不能下单");
        }
        AtomicInteger amount = new AtomicInteger(0);
        for (ShoppingCart shoppingCart : shoppingCarts){
            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrderId(orderId);
        }
        //向订单表插入一条数据
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null?"":addressBook.getProvinceName())+
                          (addressBook.getCityName() == null?"":addressBook.getCityName())+
                          (addressBook.getDistrictName()==null?"":addressBook.getDistrictName())+
                          (addressBook.getDetail()==null?"":addressBook.getDetail()));
        //向订单明细表插入多条数据

        //清空购物车数据
    }

}
