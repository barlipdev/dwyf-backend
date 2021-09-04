package com.barlipdev.dwyf.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class JwtFilter extends BasicAuthenticationFilter {

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationByToken(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("7aPc$VED<-Qr8)E".getBytes())
                .parseClaimsJws(header.replace("Bearer ",""));

        String username = claimsJws.getBody().get("email").toString();
        String userRole = claimsJws.getBody().get("userRole").toString();
        String password = claimsJws.getBody().get("password").toString();

        Set<SimpleGrantedAuthority> role = Collections.singleton(new SimpleGrantedAuthority(userRole));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username,password,role);

        return usernamePasswordAuthenticationToken;
    }
}
