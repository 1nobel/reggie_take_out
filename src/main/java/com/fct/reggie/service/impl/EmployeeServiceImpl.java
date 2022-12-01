package com.fct.reggie.service.impl;

import com.fct.reggie.dao.EmployeeMapper;
import com.fct.reggie.pojo.Employee;
import com.fct.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee selectByNamePwd(String username, String password) {
        return employeeMapper.selectByNamePwd(username, password);
    }

    @Override
    public boolean save(Employee employee) {
        Integer save = employeeMapper.save(employee);
        if(save == 1){
            return true;
        }else
        return false;
    }

    @Override
    public List<Employee> page(int page, int pageSize, String name) {
//        如果输入框中name为null则自动转为“”串
        if(name == null){
            name = "";
        }
//        分页查询中limit的第一个参数
        int a = (page-1)*pageSize;
        return employeeMapper.page(a, pageSize, name);
    }

    @Override
    public Long selectCount(String name) {
        if(name == null){
            name = "";
        }
        Long count = employeeMapper.selectCount(name);
//        如果查询到的结果为空，则将值转为0
        if(count == null){
            count = Long.valueOf(0);
        }
        return count;
    }

    @Override
    public boolean update(Employee employee) {
        Integer update = employeeMapper.update(employee);
        if(update != 0){
            return true;
        }else
            return false;
    }

    @Override
    public Employee selectById(Integer id) {
        return employeeMapper.selectById(id);
    }
}
