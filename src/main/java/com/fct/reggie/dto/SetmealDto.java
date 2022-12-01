package com.fct.reggie.dto;


import com.fct.reggie.pojo.Setmeal;
import com.fct.reggie.pojo.SetmealDish;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

    private Long id;

    //分类id
    private Long categoryId;


    //套餐名称
    private String name;


    //套餐价格
    private BigDecimal price;


    //状态 0:停用 1:启用
    private Integer status;


    //编码
    private String code;


    //描述信息
    private String description;


    //图片
    private String image;

    private LocalDateTime createTime;


    private LocalDateTime updateTime;


    private Long createUser;


    private Long updateUser;
}
