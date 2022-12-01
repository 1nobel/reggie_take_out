package com.fct.reggie.service;

import com.fct.reggie.pojo.AddressBook;

import java.util.List;

public interface AddressBookService {
    void save(AddressBook addressBook);

    List<AddressBook> list(Long userId );

    Integer update(AddressBook addressBook);

    Integer updateById(AddressBook addressBook);

    AddressBook getById(Long id);

    Integer delete(Long id);

    AddressBook getByUserIdDefault(Long userId);
}
