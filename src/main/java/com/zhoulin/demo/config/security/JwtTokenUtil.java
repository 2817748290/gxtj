package com.zhoulin.demo.config.security;


import com.zhoulin.demo.bean.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Component
public class JwtTokenUtil {
    /**
     * token过期时间
     */
    @Value("${jwt.validity-time}")
    private long validityTime = 60 * 60 * 2 * 1000; // 2 hours  validity
    /**
     * header中标识
     */
    @Value("${jwt.header}")
    private String header;
    /**
     * 加密密钥
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 验签
     */
    public Optional<Authentication> verifyToken(HttpServletRequest request, HttpServletResponse response) {
        final String token = request.getHeader(header);
        if (token != null && !token.isEmpty()){
            final UserInfo user = parse(token.trim());
            if (user != null) {
                if(user.getGmtModified().getTime() < System.currentTimeMillis()){
                    response.addHeader("refresh",create(user));
                }
                return Optional.of(new TokenUserAuthentication(user, true));
            }
        }
        return Optional.empty();
    }

    /**
     * 验签
     */
    public UserInfo verifyToken(String token) {
        if (token != null && !token.isEmpty()){
            final UserInfo user = parse(token.trim());
            if (user != null) {
                return user;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * 从用户中创建一个jwt Token
     * @param userDTO 用户
     * @return token
     */
    public String create(UserInfo userDTO) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + validityTime))
                .setIssuedAt(new Date(System.currentTimeMillis() + validityTime/5*4))
                .claim("username",userDTO.getUsername())
                .claim("user_id", userDTO.getUserId())
                .claim("nickname", userDTO.getNickname())
                .claim("roles", userDTO.getRoles())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 从token中取出用户
     */
    public UserInfo parse(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("zhoulinSecret")
                .parseClaimsJws(token)
                .getBody();
        UserInfo userDTO = new UserInfo();
        userDTO.setUserId(claims.get("user_id",Integer.class));
        userDTO.setNickname(claims.get("nickname",String.class));
        userDTO.setUsername(claims.get("username",String.class));
        userDTO.setRoles((List<String>) claims.get("roles"));
        userDTO.setGmtModified(claims.getIssuedAt());
        return userDTO;
    }

}
