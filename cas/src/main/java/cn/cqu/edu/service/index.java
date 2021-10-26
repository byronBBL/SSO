package cn.cqu.edu.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class index {
    @GetMapping("/cas")
    public String goIndex(){
        System.out.println("index");
        return "index";
    }
    @PostMapping("/cas")
    public String postIndex(){
        System.out.println("post index");
        return "index";
    }
}
