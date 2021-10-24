package cn.cqu.edu.HandlerInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class MyConfigurer implements WebMvcConfigurer {
//     /**
//      * 视图映射
//      * @param registry
//      */
//     @Override
//     public void addViewControllers(ViewControllerRegistry registry) {
//         registry.addViewController("/").setViewName("login");
//         registry.addViewController("/index").setViewName("login");
//         registry.addViewController("/main.html").setViewName("redirect:students");
//     }

// }
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public MyWebMvcConfig getMyWebMvcConfig(){
        MyWebMvcConfig myWebMvcConfig = new MyWebMvcConfig() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("alogin");
                registry.addViewController("/login").setViewName("alogin");
                registry.addViewController("/main.html").setViewName("dashboard");
            }
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerIntercep()).addPathPatterns("/**")
                        .excludePathPatterns("/login","/","/user/login");
            }
        };
        return myWebMvcConfig;
    }
    }