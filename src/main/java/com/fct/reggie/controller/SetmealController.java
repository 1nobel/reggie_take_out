package com.fct.reggie.controller;

import com.fct.reggie.common.Result;
import com.fct.reggie.dto.DishDto;
import com.fct.reggie.dto.SetmealDto;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.pojo.Setmeal;
import com.fct.reggie.pojo.SetmealDish;
import com.fct.reggie.service.CategoryService;
import com.fct.reggie.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    SetmealService setmealService;

    @Autowired
    CategoryService categoryService;

    /**
     * 添加套餐
     * @param setmealDto 前端传入复合类型数据
     * @return
     */
    @CacheEvict(value = "setmealCache", allEntries = true)
    @PostMapping
    @ApiOperation(value = "新增套餐接口")
    public Result<String> insert(@RequestBody SetmealDto setmealDto){
        setmealService.insert(setmealDto);
        return Result.success(1,"添加套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "套餐分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "name",value = "套餐名称", required = false)
    })
    public Result<Page> page(Integer page, Integer pageSize, String name){
        Page<Setmeal> page1 = setmealService.page(page, pageSize, name);
        Page<SetmealDto> page2 = new Page<>();
        //将页面1复制给页面2，records的内容除外
        BeanUtils.copyProperties(page1, page2, "records");

        List<SetmealDto> setmealDtos = new ArrayList<>();
        List<Setmeal> records = page1.getRecords();
        for (Setmeal record : records ){
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record,setmealDto);
            Long categoryId = record.getCategoryId();
            setmealDto.setCategoryName(categoryService.selectById(categoryId) );
            setmealDtos.add(setmealDto);
        }
        page2.setRecords(setmealDtos);
        return Result.success(1, page2);
    }

    /**
     * 删除套餐信息
     * 清除分类缓存下的所有数据
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmealCache", allEntries = true)
    @DeleteMapping
    @ApiOperation(value = "删除套餐信息")
    @ApiImplicitParam(name = "ids",value = "套餐id",required = true)
    //@RequestParam把请求中的指定名称的参数传递给控制器中的形参赋值
    public Result<String> delete(@RequestParam List<Long> ids){
        setmealService.remove(ids);
        return Result.success(1,"删除成功！！！");

    }




}
