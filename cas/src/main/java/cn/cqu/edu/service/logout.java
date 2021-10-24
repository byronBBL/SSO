package cn.cqu.edu.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class logout {
    @RequestMapping("/cas/logout")
    public void doLogout(HttpServletRequest req, HttpServletResponse resp){
        Cookie[] cookies=req.getCookies(); // 判断之前有没有登陆过cas
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("JWT")){ // 同名cookie会直接覆盖
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
    }
}
