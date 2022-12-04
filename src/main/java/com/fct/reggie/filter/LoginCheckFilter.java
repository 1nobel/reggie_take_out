package com.fct.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.fct.reggie.common.BaseContext;
import com.fct.reggie.common.Code;
import com.fct.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns="/*")
public class LoginCheckFilter implements Filter {
//    1.配置路径匹配器
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
//        2.获取请求路径
        String url = request.getRequestURI();
//        2.写出要放行的请求路径
        String[] urls = new String[]{
                "/employee/logout",
                "/employee/login",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login", //移动端登录
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
//        3.如果访问的是可放行页面则放行
        if(check(urls,url) == true){
            log.info("本次请求的路径为：{}可放行",url);
            filterChain.doFilter(request,response);
            return;
        }
        //4-1.如果用户已登录则放行（检查session）
        Integer empId = (Integer)request.getSession().getAttribute("employee");
        if(empId!= null){
            log.info("用户已登录,用户id为：{}",request.getSession().getAttribute("employee"));
            long id = Thread.currentThread().getId();
            BaseContext.setCurrentId(Long.valueOf(empId));
            log.info("线程id为：{}",id);
            filterChain.doFilter(request,response);
            return;
        }


        //4-2  判断用户是否登录，如果登录则直接放行
        Long userId = (Long)request.getSession().getAttribute("user");
        if(userId != null){
            log.info("用户已登录,用户id为：{}",request.getSession().getAttribute("user"));
            long id = Thread.currentThread().getId();
            BaseContext.setCurrentId(userId);
            log.info("线程id为：{}",id);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录：{}",url);
        response.getWriter().write(JSON.toJSONString(Result.error(Code.LOGIN_ERR,"NOTLOGIN")));
        return ;
    }
    public boolean check(String[] urls,String requestUrl) {
        for (String url : urls) {
            if (ANT_PATH_MATCHER.match(url, requestUrl)) {
                return true;
            }
        }
        return false;
    }
}
