package cn.cqu.edu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller 
public class login {
    @RequestMapping("/cas/login")
    public String doLogin(){
        System.out.println("登录");
        return "login";
    }
}
