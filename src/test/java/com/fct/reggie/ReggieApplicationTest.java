package com.fct.reggie;

import com.fct.reggie.dao.EmployeeMapper;
import com.fct.reggie.pojo.Employee;
import com.fct.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
public class ReggieApplicationTest {
    @Autowired
    public EmployeeMapper employeeMapper;
    @Autowired
    EmployeeService employeeService;
    @Test
    public void testLogin(){
        Employee employee = employeeMapper.selectByNamePwd("admin","");
//        log.info(employee);
        System.out. println(employee);
    }
    @Test
    public void testService(){
        Employee admin = employeeService.selectByNamePwd("admin", "");
//        log.info(admin.toString());
        System.out.println(admin);
    }
}
