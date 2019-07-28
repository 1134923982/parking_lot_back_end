package com.oocl.ita.parkinglot.interceptor;

import com.oocl.ita.parkinglot.utils.CrossOriginUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossOriginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        CrossOriginUtil.responseSet(response, request.getHeader("Origin"));
        return true;
    }
}