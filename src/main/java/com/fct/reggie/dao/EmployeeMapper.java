package com.fct.reggie.dao;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

//  根据用户名和密码查询
    public Employee selectByNamePwd(@Param("username") String username,@Param("password") String password);

//  添加新用户
    @AutoFill(type = AutoFillConstant.INSERT)
    public Integer save(Employee employee);

//  分页查询
    public List<Employee> page(@Param("a")int a, @Param("pageSize") int pageSize, @Param("name") String name);

//  查询总数据数
    public Long selectCount(@Param("name")String name);

//  根据id修改信息
    @AutoFill(type = AutoFillConstant.UPDATE)
    public Integer update(Employee employee);

    public Employee selectById(Integer id);
}
