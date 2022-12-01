package com.fct.reggie.controller;

import com.fct.reggie.common.BaseContext;
import com.fct.reggie.common.Result;
import com.fct.reggie.pojo.AddressBook;
import com.fct.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public Result<AddressBook> save(@RequestBody AddressBook addressBook) {
        //设置当前用户id
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        //将地址信息添加到数据库
        addressBookService.save(addressBook);
        //返回成功或失败信息
        return Result.success(1,addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    public Result<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);

        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.update(addressBook);
//
        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return Result.success(1,addressBook);
    }

    /**
     * 根据id查询地址，回显以便进行修改
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return Result.success(1, addressBook);
        } else {
            return Result.error(0, "没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public Result<AddressBook> getDefault() {

        Long userId = BaseContext.getCurrentId();

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getByUserIdDefault(userId);

        if (null == addressBook) {
            return Result.error(0,"没有找到该对象");
        } else {
            return Result.success(1,addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);

        //条件构造器
        List<AddressBook> addressBooks = addressBookService.list(addressBook.getUserId());

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return Result.success(1,addressBooks);
    }

    /**
     * 编辑收货地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody AddressBook addressBook){
        log.info("addressBook:{}", addressBook);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.updateById(addressBook);
        return Result.success(1,"地址修改成功");
    }

    @DeleteMapping
    public Result<String> delete(Long ids){
        addressBookService.delete(ids);
        return Result.success(1,"地址删除成功");
    }

}
