package com.jwtLogin.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jwtLogin.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtInterceptor implements HandlerInterceptor{

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	AuthorizationExtractor authorizationExtractor;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(request.getMethod().equals("OPTIONS")) {
			System.out.println("Test ::"+token);
    		return true;
    	}
		
		//final String requestTokenHeader = authorizationExtractor.extract(request, "Bearer");
		String userEmail = null;
        String jwtToken = null;
        System.out.println("test22222 :: "+token);
        
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (token != null && token.startsWith("Bearer ")) {
            jwtToken = token.substring(7);
            try {
            	userEmail = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("JWT 토큰을 가져올 수 없습니다.");
                
            } catch (ExpiredJwtException e) {
                System.out.println("JWT 토큰이 만료되었습니다");
            
            }
        } else {
        	System.out.println("JWT 토큰이 존재하지 않습니다.");
        	return true;
        
        }

		return true;
	}

}
