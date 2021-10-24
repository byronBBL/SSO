package cn.cqu.edu.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cqu.edu.JwtTools.JwtUtils;
import cn.cqu.edu.domain.UserRepository;


@RestController
public class authenticate {
    @Autowired // 自动装配成mongo数据库
    private  UserRepository userRepository;
    
    @PostMapping("/cas/authenticate") // 验证是否密码正确
    public void doAuthenticate(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        String LOCAL_SERVICE=null;
        LOCAL_SERVICE=req.getParameter("LOCAL_SERVICE");
        String user_id=req.getParameter("username");
        String pwd=req.getParameter("password");
        boolean correct=true;
        System.out.println("username:"+user_id);
        String pwd_db=userRepository.findById(user_id).get().getPassword();
        System.out.println(user_id+"的密码是："+pwd_db+",输入的密码是："+pwd);
        correct=pwd_db.equals(pwd);
        if(correct){ // 密码正确
            System.out.println("密码正确跳转到原页面并生成SSO域下的JWT");
            String JWT=JwtUtils.createToken(user_id);
            Cookie jwt_cookie=new Cookie("JWT",JWT);
            jwt_cookie.setMaxAge(60*5);
            resp.addCookie(jwt_cookie);
            if (LOCAL_SERVICE!=null){
                resp.sendRedirect(LOCAL_SERVICE+"?jwt_cookie="+jwt_cookie);
            }
            else{
                // resp.sendRedirect(req.getContextPath()+"/index.jsp");
                resp.sendRedirect("http://localhost:8080/cas"); 
            }
        }
        else{ // 密码错误验证失败重新输入
            resp.sendRedirect("http://localhost:8080/cas/login"); 
        }
    }
    @GetMapping("/cas/authenticate") // 验证是否密码正确
    public void GetAuthentication(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        String user_id=null;
        Cookie[] cookies=req.getCookies(); // 判断之前有没有登陆过cas
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("JWT")){ 
                    user_id=JwtUtils.getPayload(cookie.getValue()); // 判断是否有效，若有效说明之前已经有系统登陆过了，所以这次这个系统不用再登录了
                }
            }
        }
        System.out.println(user_id);
        if(user_id!=null){ // 说明有系统已经之前登录过
            System.out.println("SSO域下已经有JWT了直接跳转到原页面生成APP_JWT");
            String APP_JWT=JwtUtils.createToken(user_id);
            String LOCAL_SERVICE=req.getParameter("LOCAL_SERVICE"); // 原页面
            // Cookie jwt_cookie=new Cookie("APP_JWT",APP_JWT);
            if(LOCAL_SERVICE!=null){
                resp.sendRedirect(LOCAL_SERVICE+"?APP_JWT="+APP_JWT); // app的本地JWT
            }
            else{
                resp.sendRedirect("http://localhost:8080/cas");  
            }
        }
        else{ // 之前没有系统登录过，则必须要登录,因为要将请求信息传递给login页面所以这里没有用sendredirect
            System.out.println("之前没有登录过需要登录一次");
            req.getRequestDispatcher("/cas/login").forward(req,resp);
        }
    }
}
