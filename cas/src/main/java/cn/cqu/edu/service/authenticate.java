package cn.cqu.edu.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.cqu.edu.JwtTools.JwtUtils;
import cn.cqu.edu.domain.UserRepository;

@Controller
public class authenticate {
    @Autowired // 自动装配成mongo数据库
    private UserRepository userRepository;

    @PostMapping("/cas/Authenticate") // 验证是否密码正确
    public String postAuthentication(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String LOCAL_SERVICE = null;
        boolean correct = true;
        LOCAL_SERVICE = req.getParameter("LOCAL_SERVICE");
        String userId = req.getParameter("username");
        String pwd = req.getParameter("password");
        if (userRepository.findById(userId).isEmpty()) { // 不存在该用户
            System.out.println("没有账号名为" + userId + "的用户");
        } else {
            String pwd_db = userRepository.findById(userId).get().getPassword();
            System.out.println(userId + "的密码是：" + pwd_db + ",输入的密码是：" + pwd);
            correct = pwd_db.equals(pwd);
        }
        if (correct) { // 密码正确
            System.out.println(userId + "密码正确登录成功！跳转到原页面并生成CAS域下的JWT");
            String CAS_JWT = JwtUtils.createToken(userId);
            Cookie CAS_JWT_Cookie = new Cookie("CAS_JWT", CAS_JWT);
            CAS_JWT_Cookie.setMaxAge(60 * 5);
            resp.addCookie(CAS_JWT_Cookie);
            if (LOCAL_SERVICE != null) {
                System.out.println("CAS_JWT:" + CAS_JWT);
                System.out.println("成功返回" + LOCAL_SERVICE);
                resp.sendRedirect(LOCAL_SERVICE + "?CAS_JWT=" + CAS_JWT);
            } else {
                System.out.println("没有原页面直接进入CAS主页面");
                return "index";
            }
        } else { // 密码错误，验证失败，重新输入
            System.out.println("密码错误，验证失败，重新回到登录界面");
            resp.sendRedirect("http://localhost:8080/cas/login?LOCAL_SERVICE=" + LOCAL_SERVICE);
            // return "login";
        }
        return null;
    }

    @GetMapping("/cas/Authenticate") // 验证是否密码正确
    public String GetAuthentication(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String LOCAL_SERVICE = null;
        String APP_JWT = null, CAS_JWT = null;
        String cas_userId = null, app_userId = null;
        Cookie[] cookies = req.getCookies(); // 判断之前有没有登陆过cas
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CAS_JWT")) {
                    cas_userId = JwtUtils.getPayload(cookie.getValue()); // 判断之前是否登录过cas统一认证平台
                }
            }
        }
        if (cas_userId != null) { // 说明之前登录过cas平台
            APP_JWT = req.getParameter("APP_JWT"); // 验证APP_JWT是否有效
            if(APP_JWT!=null) app_userId = JwtUtils.getPayload(APP_JWT);
            System.out.println("cas_userId:"+cas_userId+" app_userId:"+app_userId);
            // app的id必须和cas的id相同才能说明app的jwt有效，否则app的jwt可能是过时的，cas的jwt才是新的
            if (app_userId != null && app_userId.equals(cas_userId)) {  // 字符串是对象不能直接用等号判断相等！！！！
                System.out.println(cas_userId + "app下的令牌有效，成功返回数据");
                LOCAL_SERVICE = req.getParameter("LOCAL_SERVICE");
                // resp.sendRedirect(LOCAL_SERVICE+"?validToken=true");
                // resp.sendRedirect("http://localhost:8080/cas/validToken?LOCAL_SERVICE="+LOCAL_SERVICE);
                return "valid";
                // 这里为了安全没有直接显式的用get在url上面传递参数而是用post隐藏参数传递，这样可以避免伪造url地址就可以直接访问app页面了
            } else { // 说明当前app要么还没有生成本地的令牌，要么是生成的令牌过期了或者被修改了需要重新生成
                System.out.println(cas_userId + "已经登录过CAS，跳转到原页面通过CAS域下已有的JWT生成本地的APP_JWT令牌");
                LOCAL_SERVICE = req.getParameter("LOCAL_SERVICE");
                CAS_JWT = JwtUtils.createToken(cas_userId);
                if (LOCAL_SERVICE != null) {
                    resp.sendRedirect(LOCAL_SERVICE + "?CAS_JWT=" + CAS_JWT); // app的本地JWT
                } else { // 没有原页面返回到认证主页面
                    return "index";
                }
            }
        } else { // 之前没有系统登录过统一认证CAS平台，则必须要登录一次
            System.out.println("之前没有登录过统一认证平台CAS，跳转到登录界面进行登录");
            // req.getRequestDispatcher("/cas/login").forward(req,resp);
            LOCAL_SERVICE = req.getParameter("LOCAL_SERVICE");
            if (LOCAL_SERVICE != null)
                resp.sendRedirect("http://localhost:8080/cas/login?LOCAL_SERVICE=" + LOCAL_SERVICE);
            else
                resp.sendRedirect("http://localhost:8080/cas/login");
        }
        return null;
    }
}
