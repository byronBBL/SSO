package cn.cqu.edu.Interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        Cookie[] cookies = request.getCookies();
        String validToken = null,APP_JWT=null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("APP_JWT".equals(cookie.getName())) { // 有本地令牌
                    APP_JWT=cookie.getValue();
                    validToken = request.getParameter("validToken");
                    if (validToken != null && validToken.equals("true")) { // 令牌有效，不再进行拦截
                        System.out.println("APP_JWT为有效令牌");
                        return true;
                        // 若在CAS端验证的令牌是无效的，会在CAS进行登录重新返回有效的CAS_JWT,APP再利用有效的CAS_JWT生成本地的APP_JWT
                    }
                }
            }
        }
        String CAS_JWT = request.getParameter("CAS_JWT");
        System.out.println("CAS_JWT:" + CAS_JWT);
        if (CAS_JWT != null) { // CAS网址下存在CAS_JWT，利用返回的CAS_JWT生成本地APP_JWT
            // 设置本地cookie
            System.out.println("CAS网址下存在CAS_JWT，利用返回的CAS_JWT生成本地APP_JWT");
            Cookie APP_JWT_Cookie = new Cookie("APP_JWT", CAS_JWT);
            APP_JWT_Cookie.setMaxAge(60 * 5);
            APP_JWT_Cookie.setPath("/app2");
            response.addCookie(APP_JWT_Cookie);
            response.sendRedirect(String.valueOf(request.getRequestURL())); 
            // 有了本地APP_JWT再重新访问该页面，此时还是会再次进入cas进行验证，因为传过来的CAS_JWT可能是被伪造的，所以还要到cas验证jwt的正确性
        } else {
            System.out.println("没有收到CAS发过来的令牌");
            if(APP_JWT!=null){
                System.out.println("APP本地存在JWT,跳转到CAS认证JWT的有效性");
                response.sendRedirect("http://localhost:8080/cas/Authenticate?LOCAL_SERVICE=" + request.getRequestURL()+"&APP_JWT="+APP_JWT);
            } 
            else{
                System.out.println("APP本地不存在JWT,跳转到CAS进行认证，若有子系统登录过则直接返回CAS_JWT,否则先登录CAS再返回");
                response.sendRedirect("http://localhost:8080/cas/Authenticate?LOCAL_SERVICE=" + request.getRequestURL());
            }
            return false;
        }
        return false;
    }

}