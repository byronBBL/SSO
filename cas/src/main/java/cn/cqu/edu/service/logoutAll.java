package cn.cqu.edu.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class logoutAll {
    @RequestMapping("/cas/logoutAll")
    public String doLogoutAll(){
        System.out.println("注销所有系统的令牌");
        return "logoutAll";
    }
}
