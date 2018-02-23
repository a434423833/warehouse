package com.weiyebancai.warehouse.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置
 */
@Configuration
public class MyWebUserInterceptor extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        InterceptorRegistration ir = registry.addInterceptor(new UserInterceptor());
        ir.addPathPatterns("/insertProduct");
        ir.addPathPatterns("/delectProduct");
        InterceptorRegistration ira = registry.addInterceptor(new UserAuthInterceptor());
        ira.excludePathPatterns("/userLogin");
        ira.excludePathPatterns("/");
        ira.excludePathPatterns("/exit");
        ira.addPathPatterns("/*");
    }

}
