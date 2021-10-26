package cn.cqu.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class index {
    @RequestMapping("/app2")
    public String Index(){
        return "index";
    }
}
