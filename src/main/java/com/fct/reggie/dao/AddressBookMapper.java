package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    //添加地址信息
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer save(AddressBook addressBook);

    //查询所有地址
    List<AddressBook> list(Long userId );

    //设置默认地址
    Integer update(AddressBook addressBook);

    //根据id更新
    Integer updateById(AddressBook addressBook);

    //根据id查询
    AddressBook getById(Long id);

    //删除地址
    Integer delete(Long id);

    //查询默认地址信息
    AddressBook getByUserIdDefault(Long userId);
}
