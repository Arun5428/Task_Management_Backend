package com.example.task.user.services.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidater extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String jwt=request.getHeader(JwtConstrant.JWT_HEADER);
        System.out.println("jwt>>>>"+jwt);
        if(jwt!=null){
            jwt=jwt.substring(7);
            try{
                SecretKey key= Keys.hmacShaKeyFor(JwtConstrant.SECRET_KEY.getBytes());
                Claims claims =Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                String emails=String.valueOf(claims.get("email"));
                String authoritiees=String.valueOf(claims.get("authorities"));
                List<GrantedAuthority>auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiees);
                Authentication authentication=new UsernamePasswordAuthenticationToken(emails,null,auth);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch (Exception e){
                throw new BadCredentialsException("invalied tokens");


            }
        }
        filterChain.doFilter(request,response);

    }
}
