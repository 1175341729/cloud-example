package com.springcloud.example.common.config;

import com.springcloud.example.common.interceptor.SignInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/***
 *  @Author dengwei
 *  @Description: 拦截器注册
 *  @Date 2019/6/3 15:29
 */
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
    @Resource
    private SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor).addPathPatterns("/sign/**");
    }
}
