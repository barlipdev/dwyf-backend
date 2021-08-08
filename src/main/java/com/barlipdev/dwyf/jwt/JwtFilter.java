package com.barlipdev.dwyf.jwt;

import com.barlipdev.dwyf.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends BasicAuthenticationFilter {

    @Autowired
    UserRepository userRepository;

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authToken = getAuthenticationByToken(header);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {

        StringBuffer stringBuffer = new StringBuffer(header);
        for (int i=0;i<27;i++){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        header = stringBuffer.toString();

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("asffddfs$%&*".getBytes())
                .parseClaimsJws(header.replace("Bearer ", ""));

        String email = claimsJws.getBody().get("email").toString();
        String password = claimsJws.getBody().get("password").toString();
        String id = claimsJws.getBody().get("id").toString();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);
        return usernamePasswordAuthenticationToken;
    }
}
