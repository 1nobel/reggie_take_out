package com.fct.reggie.controller;

import com.fct.reggie.common.Code;
import com.fct.reggie.common.Result;
import com.fct.reggie.pojo.Employee;
import com.fct.reggie.pojo.Page;
import com.fct.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /**
     * 员工登录
     * @RequestBody传递json数据
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
//        1.获取后台发送的信息
        String username = employee.getUsername();
        String password = employee.getPassword();
//        2.将密码进行MD5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        3.调用service方法进行查询
        Employee emp = employeeService.selectByNamePwd(username,password);
//        4.如果查询为null则登录失败
        if(emp == null){
            return Result.error(Code.LOGIN_ERR,"登陆失败，用户名或密码错误");
        }
//        5.查看用户状态，为0则返回用户已禁用
        if(emp.getStatus() == 0){
           return Result.error(Code.LOGIN_ERR,"用户已禁用");
        }
//        6.登陆成功，将用户Id保存到session中
        request.getSession().setAttribute("employee",emp.getId());
        log.info(employee.toString());
        return Result.success(Code.LOGIN_OK,emp);

    }

    /**
     * 用户退出功能，删除session
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<Employee> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return Result.success(Code.LOGIN_OK,"退出成功");
    }

    /**
     * 添加用户功能
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增用户的信息为：{}",employee.toString());
//        1.为用户添加默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//    private Long createUser;
//    private Long updateUser;
//        2.为用户添加创建的时间，更新时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        3.根据session获取用户的id
//        Integer userId =(Integer)request.getSession().getAttribute("employee");
//        employee.setCreateUser(Long.valueOf(userId));
//        employee.setUpdateUser(Long.valueOf(userId));
        Boolean flag = employeeService.save(employee);

        return flag?Result.success(Code.LOGIN_OK,"添加用户成功"):Result.error(Code.LOGIN_ERR,"添加用户失败");
    }

    /**
     * 分页查询
     * @param page 前端传入页号
     * @param pageSize 前端传入页面大小
     * @param name 前端传入要查询的字段名
     * @return 返回的数据中包含page.records和page.total
     */
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize, String name){
        Page<Employee> pages = new Page<>();
        log.info("查询第{}页，页面大小为{}，筛选条件为{}",page,pageSize,name);
        List<Employee> list = employeeService.page(page, pageSize, name);
//        log.info(String.valueOf(list));
//       调用page的setRecords设置查询到的数据
        pages.setRecords(employeeService.page(page, pageSize, name));
//        查询到的、数据数量
        pages.setTotal(employeeService.selectCount(name));
        return Result.success(Code.LOGIN_OK,pages);
    }

    @PutMapping
    public Result<String> update(HttpServletRequest request,@RequestBody Employee employee){
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        log.info(employee.toString());
//        设置更新时间
//        employee.setUpdateTime(LocalDateTime.now());
//        设置更新人，更新人信息（id）从session中获取
//        Integer userId =(Integer)request.getSession().getAttribute("employee");
//        employee.setUpdateUser(Long.valueOf(userId));
        boolean update = employeeService.update(employee);
        log.info(employee.toString());
//        update = employeeService.update(employee);
        if (update == true){
        return Result.success(Code.LOGIN_OK,"修改状态成功");
        }
        else
            return Result.error(Code.LOGIN_ERR,"修改状态失败");
    }

    /**
     * 修改用户信息，回显
     * @param id 前端获取到的用户id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Integer id) {
        Employee employee = employeeService.selectById(id);
        if (employee != null) {
            return Result.success(Code.LOGIN_OK, employee);
        }
        else{
            return Result.error(Code.LOGIN_ERR,"修改信息错误");
        }
    }

}
