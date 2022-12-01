package com.fct.reggie.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品分类实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    //      序列号
    private static final long serialVersionUID = 8545996863226528800L;

    //      菜品id
    private Long id;

    //      类型：1.菜品分类 2.套餐分类
    private Integer type;

    //      分类名称
    private String name;

    //      顺序
    private Integer sort;

    //     创建时间
    private LocalDateTime createTime;

    //     更新时间
    private LocalDateTime updateTime;

    //    创建人
    private Long createUser;

    //    更新人
    private Long updateUser;
}
