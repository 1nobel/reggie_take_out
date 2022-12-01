package com.fct.reggie.dto;

import com.fct.reggie.pojo.Dish;
import com.fct.reggie.pojo.DishFlavor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;


    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public Long getCategoryId() {
        return super.getCategoryId();
    }

    public void setCategoryId(Long categoryId) {
        super.setCategoryId(categoryId);
    }

    public BigDecimal getPrice() {
        return super.getPrice();
    }

    public void setPrice(BigDecimal price) {
        super.setPrice(price);
    }

    public String getCode() {
        return super.getCode();
    }

    public void setCode(String code) {
        super.setCode(code);
    }

    public String getImage() {
        return super.getImage();
    }

    public void setImage(String image) {
        super.setImage(image);
    }

    public String getDescription() {
        return super.getDescription();
    }

    public void setDescription(String description) {
        super.setDescription(description);
    }

    public Integer getStatus() {
        return super.getStatus();
    }

    public void setStatus(Integer status) {
        super.setStatus(status);
    }

    public Integer getSort() {
        return super.getSort();
    }

    public void setSort(Integer sort) {
        super.setSort(sort);
    }

    public LocalDateTime getCreateTime() {
        return super.getCreateTime();
    }

    public void setCreateTime(LocalDateTime createTime) {
        super.setCreateTime(createTime);
    }

    public LocalDateTime getUpdateTime() {
        return super.getUpdateTime();
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        super.setUpdateTime(updateTime);
    }

    public Long getCreateUser() {
        return super.getCreateUser();
    }

    public void setCreateUser(Long createUser) {
        super.setCreateUser(createUser);
    }

    public Long getUpdateUser() {
        return super.getUpdateUser();
    }

    public void setUpdateUser(Long updateUser) {
        super.setUpdateUser(updateUser);
    }

    public Integer getIsDeleted() {
        return super.getIsDeleted();
    }

    public void setIsDeleted(Integer isDeleted) {
        super.setIsDeleted(isDeleted);
    }


}
