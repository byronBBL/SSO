package cn.cqu.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class test {
    @RequestMapping("/test")
    public String tes(){
        System.out.println("test被访问了");
        return "index";
    }
}
