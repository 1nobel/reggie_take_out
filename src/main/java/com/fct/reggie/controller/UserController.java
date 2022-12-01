package com.fct.reggie.controller;

import com.fct.reggie.common.Result;
import com.fct.reggie.pojo.User;
import com.fct.reggie.service.UserService;
import com.fct.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 移动端发送短信
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {

        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);
            //将要生成的验证码保存到session
            session.setAttribute(phone, code);

            return Result.success(1, "手机验证码发送成功！");
        }

        return Result.error(0, "手机验证码发送失败！");
    }


    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession httpSession) {
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        String sessionCode = (String) httpSession.getAttribute(phone);

        //进行验证码的比对（页面提交的验证码和session中保存的验证码进行比对）
        if (sessionCode != null && sessionCode.equals(code)) {
            //比对成功，则登录成功
            User user = userService.getMessage(phone);
            //判断当前手机号对应的用户是否为新用户，如果是则自动完成注册
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.insert(user);
            }
            user = userService.getMessage(phone);
            httpSession.setAttribute("user", user.getId());
            return Result.success(1, user);
        }
        return Result.error(0,"登陆失败！");
    }
}
