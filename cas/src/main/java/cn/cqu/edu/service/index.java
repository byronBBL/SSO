package cn.cqu.edu.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class index {
    @RequestMapping("/cas")
    public String goIndex(){
        return "index";
    }
}
