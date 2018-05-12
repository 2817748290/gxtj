package com.zhoulin.demo.config.security;

import com.zhoulin.demo.config.exception.PermissionDeniedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class VerifyTokenFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;
    public VerifyTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<Authentication> authentication = jwtTokenUtil.verifyToken(request,response);

            SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException e){
            String json = "{\"status\":-1,\"message\":\"token expired!\",\"result\":null}";
            response.getWriter().write(json);
            response.getWriter().flush();
        }
        catch (SignatureException e){
            String json = "{\"status\":-1,\"message\":\"token error!\",\"result\":null}";
            response.getWriter().write(json);
            response.getWriter().flush();
        }
        catch (JwtException e) {
            e.printStackTrace();
            response.setContentType("json");
            String json = "{\"status\":-1,\"message\":\"Login expired!\",\"result\":null}";
            response.getWriter().write(json);
            response.getWriter().flush();
        }catch (PermissionDeniedException e){
            response.setContentType("json");
            String json = "{\"status\":-2,\"message\":\"Operate Access Denied!\",\"result\":null}";
            response.getWriter().write(json);
            response.getWriter().flush();
        }
        catch (Exception e){
            e.printStackTrace();
            response.setContentType("json");
            String json = "{\"status\":0,\"message\":\"500 server error!\",\"result\":null}";
            response.getWriter().write(json);
            response.getWriter().flush();
        }
    }
}
