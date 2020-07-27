package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") //配置源  通配
                .allowedMethods("GET","POST","PUT","DELETE","HEAD") //允许的请求方式
                .allowCredentials(true)  //是否允许携带cookie
                .maxAge(1800);  //允许跨域的持续时间

    }
}
