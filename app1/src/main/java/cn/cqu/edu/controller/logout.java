package cn.cqu.edu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class logout {
    @RequestMapping("app1/logout")
    public void doLogout(HttpServletRequest request, HttpServletResponse response){
        System.out.println("成功注销一个JWT");
        Cookie cookies[] = request.getCookies();
        for (Cookie cookie:cookies) { 
            if("APP_JWT".equalsIgnoreCase(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/app1");
                response.addCookie(cookie);
            }
        }
    }
}
