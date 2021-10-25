package cn.cqu.edu.Interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public MyWebMvcConfig getMyWebMvcConfig(){
        MyWebMvcConfig myWebMvcConfig = new MyWebMvcConfig() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new MyInterceptor()).addPathPatterns("/app1/**");
            }
        };
        return myWebMvcConfig;
    }
    }