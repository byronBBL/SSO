package cn.cqu.edu.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class index {
    @RequestMapping("/cas")
    public String goIndex(){
        return "index";
    }
}
