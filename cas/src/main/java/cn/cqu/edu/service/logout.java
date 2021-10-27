package cn.cqu.edu.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class logout {
    @RequestMapping("/cas/logoutAll") 
    // 注销时只需要删除cas本地的jwt，因为app_jwt必须建立在cas_jwt的基础上才会有效，所以只要删除了cas_jwt,app_jwt检验有效性时就自然无效了
    public void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Cookie[] cookies=req.getCookies(); // 判断之前有没有登陆过cas
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("CAS_JWT")){ // 同名cookie会直接覆盖
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        resp.sendRedirect("http://localhost:8080/cas");
    }
}
