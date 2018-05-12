package com.zhoulin.demo.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public LoginFailEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * @author ligh4 2015年4月1日下午4:38:04
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
            ServletException {

        //String requestType = request.getHeader("Accept");
        //System.out.println(requestType);
       // if ("application/json, text/plain, */*".equals(requestType)) {

            response.setContentType("json");
            String json = "{\"status\":-1,\"message\":\"token is null!\",\"result\":null}";
            response.getWriter().write(json);
            response.getWriter().flush();

        //} else {
           //
           // super.commence(request, response, authException);
        //}

    }
}
