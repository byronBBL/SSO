package cn.cqu.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class index {
    @RequestMapping("/cas")
    public String goIndex(){
        System.out.println("index");
        return "CAS主页";
    }
}
