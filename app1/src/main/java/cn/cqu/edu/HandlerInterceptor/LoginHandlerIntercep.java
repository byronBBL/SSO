package cn.cqu.edu.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

public class LoginHandlerIntercep implements HandlerInterceptor {

    private static final String SECRET_KEY = "CQU_FOREVER&"; // 默认秘钥
    /**
     * 获得jwt中包含的信息,这里为用户的userid
     * @param token jwt串
     * @return userid,若为null表示jwt有问题或者超时
     */
    public static String getUserId(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace(); 一般是jwt超时
            return null;
        }
        Claim userId = jwt.getClaims().get("user_id");
        if(userId != null){
            return userId.asString();
        } else return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Object user = request.getSession().getAttribute("loginUser");
        // if (user==null){
        //     //未登录，返回登录页面
        //     request.setAttribute("msg", "请先登录");
        //     request.getRequestDispatcher("/").forward(request, response);
        //     return false;
        // }else {
        //     //进行了登录
        //     return true;
        // }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        javax.servlet.http.Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String user_id = null;
        if(cookies != null && cookies.length>0) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if("JWT1".equals(cookie.getName()))
                    user_id = getUserId(cookie.getValue());
            }
        }
         // 获得网址的参数JWT
         String JWT = httpRequest.getParameter("jwt_cookie");
         System.out.println("jwt_cookie"+JWT);
         if(JWT != null){  // 网址存在JWT，是cas已完成登录验证的返回
            // 设置本地cookie
            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("JWT1", JWT);
            cookie.setMaxAge(60*5);
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            // hide jwt message in url
            httpResponse.sendRedirect(String.valueOf(httpRequest.getRequestURL()));
//            chain.doFilter(httpRequest, httpResponse);
        }
        if(JWT == null || user_id == null){  // 网址存在JWT，是cas已完成登录验证的返回
            response.sendRedirect("http://localhost:8080/cas/login" + "?"
            + "LOCAL_SERVICE" + "="
            + httpRequest.getRequestURL());
            return false;
        }
        else {  // 已有JWT，执行网页
            request.setAttribute("user", user_id);
            return true;
        }
    }
    
}