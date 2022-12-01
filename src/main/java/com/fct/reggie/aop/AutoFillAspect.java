package com.fct.reggie.aop;

import com.fct.reggie.common.AutoFill;
import com.fct.reggie.common.AutoFillConstant;
import com.fct.reggie.common.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，统一为公共字段赋值
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    //切面 = 通知 + 切入点

    /**
     * 切入点
     */
    @Pointcut("execution(* com.fct.reggie.dao.*.*(..)) && @annotation(com.fct.reggie.common.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 通知 自动填充公共字段
     *
     * @param pjp
     */
    @Before("autoFillPointCut()")
    public void AutoFillAdvice(JoinPoint pjp) throws Throwable {
        log.info("公共字段自动填充...");

        //获得方法签名对象
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获得方法上的注解
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        //获得注解中type的值
        String type = autoFill.type(); // insert update

        //获取当前目标方法的参数
        Object[] args = pjp.getArgs();
        //数值满足下面条件时，说明没有参数直接结束
        if (args == null || args.length == 0) {
            return ;
        }

        //实体对象
        Object entity = args[0];

        //准备赋值的数据
        LocalDateTime time = LocalDateTime.now();
        Long empId = BaseContext.getCurrentId();

        if (type.equals(AutoFillConstant.INSERT)) {
            //当前执行的是insert操作，为4个字段赋值
            try {
                //获得set方法对象----Method
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通过反射调用目标对象的方法
                setCreateTime.invoke(entity, time);
                setUpdateTime.invoke(entity, time);
                setCreateUser.invoke(entity, empId);
                setUpdateUser.invoke(entity, empId);
            } catch (Exception ex) {
                log.error("公共字段自动填充失败：{}", ex.getMessage());
            }
        } else {
            //当前执行的是update操作，为2个字段赋值
            try {
                //获得set方法对象----Method
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通过反射调用目标对象的方法
                setUpdateTime.invoke(entity, time);
                setUpdateUser.invoke(entity, empId);
            } catch (Exception ex) {
                log.error("公共字段自动填充失败：{}", ex.getMessage());
            }
        }
    }

}
