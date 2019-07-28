package com.oocl.ita.parkinglot.configuration;

import com.oocl.ita.parkinglot.interceptor.CrossOriginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginConfiguration implements WebMvcConfigurer {

    @Bean
    CrossOriginInterceptor crossOriginInterceptor() {
        return new CrossOriginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossOriginInterceptor());
    }
}