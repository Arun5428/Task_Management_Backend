package com.example.task.user.services.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.util.*;


public class JwtProvider {

    static SecretKey key= Keys.hmacShaKeyFor(JwtConstrant.SECRET_KEY.getBytes());
    public static String generateToken(Authentication acess){
        Collection<? extends GrantedAuthority> authorities=acess.getAuthorities();
        String role=populatesAuthorities(authorities);
        String jwt= Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86400000 ))
                .claim("email",acess.getName()).claim("authorities",role).signWith(key).compact();
        return jwt;
    }

    public static String getEmailformjwtToken(String jwt){
        jwt=jwt.substring(7);

        Claims claims =Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String emails=String.valueOf(claims.get("email"));
        return emails;
    }

    private static String populatesAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String>auth=new HashSet<>();
        for(GrantedAuthority authority:authorities){
            auth.add(authority.getAuthority());

        }
        return String.join(",",auth);
    }


}
