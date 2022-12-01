package com.fct.reggie.service.impl;

import com.fct.reggie.dao.AddressBookMapper;
import com.fct.reggie.pojo.AddressBook;
import com.fct.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void save(AddressBook addressBook) {
        addressBookMapper.save(addressBook);
    }

    @Override
    public List<AddressBook> list(Long userId) {
        return addressBookMapper.list(userId);
    }

    @Override
    public Integer update(AddressBook addressBook) {
        return addressBookMapper.update(addressBook);
    }

    @Override
    public Integer updateById(AddressBook addressBook) {
        return addressBookMapper.updateById(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    @Override
    public Integer delete(Long id) {
        return addressBookMapper.delete(id);
    }

    @Override
    public AddressBook getByUserIdDefault(Long userId) {
        return addressBookMapper.getByUserIdDefault(userId);
    }
}
