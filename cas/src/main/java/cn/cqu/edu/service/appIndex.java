package cn.cqu.edu.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class appIndex {
    @PostMapping("/appIndexPost")
    public void doLogin(HttpServletRequest req, HttpServletResponse resp){
        System.out.println(req.getParameter("validToken"));
        System.out.println("进入主界面");
       
    }
    @GetMapping("/appIndex")
    public String doLogin2(){
        // System.out.println("登录");
        return "valid";
    }
}
