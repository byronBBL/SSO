package cn.cqu.edu.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class testResponse {
    @RequestMapping("/cas/login")
    @ResponseBody // 是用来传给前端的没办法传递给后端
    // 在使用@RequestMapping后，返回值通常解析为跳转路径，加上@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中
    // @ResponseBody就可以理解成将java的对象转换成json字符串的格式给**前端**解析（json数据格式解析比较简单）
    // 
    public String doLogin(){
        System.out.println("登录");
        return "login";
    }
}
