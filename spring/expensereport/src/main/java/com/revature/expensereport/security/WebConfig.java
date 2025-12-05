package com.revature.expensereport.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final BasicAuthInterceptor basicAuthInterceptor;
    private final JwtInterceptor jwtInterceptor;

    public WebConfig(BasicAuthInterceptor basicAuthInterceptor, JwtInterceptor jwtInterceptor) {
        this.basicAuthInterceptor = basicAuthInterceptor;
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/auth/login");
    }
}
