package cn.baizhi.config;

import cn.baizhi.interceptor.LoginInIntercepor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptOrConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInIntercepor loginInIntercepor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInIntercepor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/admin");

    }


}
