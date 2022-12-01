package com.fct.reggie.controller;

import com.fct.reggie.common.Code;
import com.fct.reggie.common.Result;
import com.fct.reggie.pojo.Category;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品分类表现层
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> insert(@RequestBody Category category) {

        boolean b = categoryService.insert(category);
        log.info("catogory:{}", category);
        if (b) {
            return Result.success(Code.LOGIN_OK, "添加分类成功");
        } else {
            return Result.error(Code.LOGIN_ERR, "添加分类失败！！！");
        }
    }

    /**
     * 分页查询
     *
     * @param page     页码
     * @param pageSize 页面容量
     * @return 返回页面实体类，存放的是数据总数和页面内容集合
     */
    @GetMapping("/page")
    public Result<Page> page(Integer page, Integer pageSize) {
        Page<Category> pages = new Page<>();

        List<Category> page1 = categoryService.page(page, pageSize);

        Integer integer = categoryService.selectCount();
        pages.setRecords(page1);

        pages.setTotal(integer);

        return Result.success(Code.LOGIN_OK, pages);
    }

    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long ids) {
        categoryService.deleteById(ids);
        return Result.success(Code.LOGIN_OK, "删除成功");
    }

    /**
     * 根据id进行修改
     *
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        Integer update = categoryService.update(category);
        if (update > 0) {
            return Result.success(Code.LOGIN_OK, "修改成功");
        } else {
            return Result.error(Code.LOGIN_ERR, "修改失败");
        }
    }

    /**
     * 菜品分类信息查询
     * @param
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        List<Category> categories = new ArrayList<>();
        if(type != null){
            categories = categoryService.selectName(type);
        }else{
            categories = categoryService.selectAll();
        }
        return Result.success(1,categories);
    }

}
