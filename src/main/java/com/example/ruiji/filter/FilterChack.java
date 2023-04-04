package com.example.ruiji.filter;

import com.alibaba.fastjson.JSON;
import com.example.ruiji.comment.BaseContext;
import com.example.ruiji.comment.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//过滤器
@WebFilter(filterName = "Filter",urlPatterns = "/*")
@Slf4j
public class FilterChack implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        log.info("拦截到请求:{}",request1.getRequestURI());


        String requestUri = request1.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/**"

        };
        //判断请求是否需要处理
        boolean check=check(urls,requestUri);
        //如果不要就放行
        if(check){
            //放行
            log.info("本次请求{}放行",requestUri);
            chain.doFilter(request1, response1);
            return;
        }
        //如果已登录就放行

        if(request1.getSession().getAttribute("employee")!=null){
            log.info("用户已登录放行");

            //将Id 封装到BaseContext中
            Long id = (Long) request1.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);

            chain.doFilter(request1, response1);
            return;
        }
        if(request1.getSession().getAttribute("user")!=null){
            log.info("用户已登录放行");

            //将Id 封装到BaseContext中
            Long id = (Long) request1.getSession().getAttribute("user");
            BaseContext.setCurrentId(id);

            chain.doFilter(request1, response1);
            return;
        }
        log.info("用户未登录");
        //如果未登录
        response1.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    //路径匹配
    public static Boolean check(String[] urls,String requestUri){
        for(String url:urls){
            boolean macth=PATH_MATCHER.match(url,requestUri);
            if(macth){
                return true;
            }
        }
        return false;
    }
}

//过滤器
