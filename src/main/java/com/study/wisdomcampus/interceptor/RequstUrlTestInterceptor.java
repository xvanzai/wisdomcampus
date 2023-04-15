package com.study.wisdomcampus.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequstUrlTestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求路径是：{},请求方式为：{}",request.getRequestURL(),request.getMethod());
//        log.info("请求路径是：{}",request.getRequestURI());
        return true;
    }
}
