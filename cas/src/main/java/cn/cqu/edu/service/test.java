package cn.cqu.edu.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class test {
    @RequestMapping("/valid")
    public String doLogin2(){
        // String info=
        System.out.println("test被访问了");
        return "valid";
    }
}
