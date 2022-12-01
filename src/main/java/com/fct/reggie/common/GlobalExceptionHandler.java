package com.fct.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 处理全局异常
 */
@ControllerAdvice(annotations = {RestController.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
//       检查错误信息是否包含输入了重复字段
        if(ex.getMessage().contains("Duplicate entry")){
            String[] strings = ex.getMessage().split(" ");
            String msg = strings[2]+" 已存在";
            return Result.error(Code.LOGIN_ERR,msg);
        }
        return Result.error(Code.LOGIN_ERR,"未知错误");

    }

    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());

        return Result.error(Code.LOGIN_ERR,ex.getMessage());

    }
}
