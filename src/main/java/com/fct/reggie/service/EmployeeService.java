package com.fct.reggie.service;

import com.fct.reggie.pojo.Employee;
import com.fct.reggie.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeService {

    public Employee selectByNamePwd(String username,String password);

    public boolean save(Employee employee);

    public List<Employee> page(int page, int pageSize, String name);

    public Long selectCount(String name);

    public boolean update(Employee employee);

    public Employee selectById(Integer id);
}
