package com.oocl.ita.parkinglot.config;

import com.oocl.ita.parkinglot.interceptor.AuthorizationCustomerInterceptor;
import com.oocl.ita.parkinglot.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *拦截器配置
 * @author Gordon
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");;
        registry.addInterceptor(new AuthorizationCustomerInterceptor()).addPathPatterns("/customers/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");;
    }
}