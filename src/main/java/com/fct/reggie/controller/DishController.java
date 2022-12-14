package com.fct.reggie.controller;

import com.fct.reggie.common.Code;
import com.fct.reggie.common.Result;
import com.fct.reggie.dto.DishDto;
import com.fct.reggie.pojo.Dish;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.service.CategoryService;
import com.fct.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDto 菜品数据
     * @return
     */
    @PostMapping
    public Result<String> dish(@RequestBody DishDto dishDto) {
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        log.info("{}", dishDto);
        dishService.insert(dishDto);

        //删除指定的key
        redisTemplate.delete(key);
        return Result.success(1, "添加成功");
//        return null;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(Integer page, Integer pageSize, String name) {
        Page<Dish> page1 = dishService.page(page, pageSize, name);
        Page<DishDto> page2 = new Page<>();

        //将除records以外的部分传赋值给page2，忽略records
        BeanUtils.copyProperties(page1, page2, "records");

        //获取dish集合数据
        List<Dish> records = page1.getRecords();

        //创建集合对象收集修改后的菜品信息
        List<DishDto> dishDtos = new ArrayList<>();
        //遍历records
        for (Dish dish : records) {
            //创建dto对象用于将菜品类别名放入
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);

            //获取分类名
            Long categoryId = dish.getCategoryId();
            dishDto.setCategoryName(categoryService.selectById(categoryId));
            dishDtos.add(dishDto);
        }
        page2.setRecords(dishDtos);
        return Result.success(1, page2);
    }

    /**
     * 查询菜品信息，用来回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.get(id);
        return Result.success(1, dishDto);
    }

    /**
     * 修改菜品信息
     * bug：当删除一个口味时对数据进行修改不会改变口味信息？
     * 解决办法：先删除菜品对应的口味信息然后再做插入操作
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {
//        动态创建key
        String key = "dish_"+dishDto.getCategoryId()+"_1";

        dishService.update(dishDto);
        //列举出指定的keys
        //Set key = redisTemplate.keys("dish_*");
        //清理缓存数据
        redisTemplate.delete(key);
        return Result.success(1, "修改菜品信息成功");
    }


    //根据分类和状态获取菜品信息
//    @GetMapping("/list")
//    public Result<List<Dish>> getList(Long categoryId, Integer status) {
//        List<Dish> dishList = new ArrayList<>();
//        if (status != null) {
//            dishList = dishService.getList2(categoryId, status);
//        } else {
//            dishList = dishService.getList(categoryId);
//        }
//        return Result.success(1, dishList);
//    }

    //根据分类和状态获取菜品信息
    @GetMapping("/list")
    public Result<List> getList(Long categoryId, Integer status) {

        //动态创建key
        String key = "dish_"+categoryId+"_"+status;

        List<DishDto> dishDtos = new ArrayList<>() ;
        List<Dish> dishList;
        //先从redis中获取缓存数据
        dishDtos = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if(dishDtos != null){
            //如果存在直接返回
            return Result.success(1,dishDtos);
        }
        dishDtos = new ArrayList<>();
        //如果不存在需要查询,并将其添加到redis
        if (status != null) {
            dishList = dishService.getList2(categoryId, status);

            for (Dish dish: dishList) {
                Long dishId = dish.getId();
                DishDto dishDto = dishService.get(dishId);
                dishDtos.add(dishDto);
            }
        } else {
            dishList = dishService.getList(categoryId);
            return Result.success(1, dishList);
        }

        //并将其添加到redis
        redisTemplate.opsForValue().set(key,dishDtos,60, TimeUnit.MINUTES);

        return Result.success(1, dishDtos);
    }
}