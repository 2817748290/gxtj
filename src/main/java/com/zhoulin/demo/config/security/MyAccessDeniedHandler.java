package com.zhoulin.demo.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("json");
        String json = "{\"status\":-2,\"message\":\"Access Url Denied!\",\"result\":null}";
        httpServletResponse.getWriter().write(json);
        httpServletResponse.getWriter().flush();
    }
}
