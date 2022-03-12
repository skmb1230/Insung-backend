package com.jwtLogin.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtLogin.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

/**
* @packageName 	: com.jwtLogin.config
* @fileName 	: JwtRequestFilter.java
* @author 		: 이승원
* @date 		: 2022.03.12
* @description 	: 프론트엔드에서 매순간 request를 요청 할 때마다 Header의 Token가 유효한지 검증
* 				  맞다면 요청사항을 수행해줍니다.
* ===========================================================
* DATE 			AUTHOR 		NOTE
* -----------------------------------------------------------
* 2022.03.12 	이승원 	최초 생성
*/

@Component
public class JwtRequestFilter extends OncePerRequestFilter{


	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		// JWT 토큰은 "베어러 토큰" 형식입니다. Bearer 단어를 제거하고 토큰만 가져옵니다.
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("JWT 토큰을 가져올 수 없습니다.");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT 토큰이 만료되었습니다.");
			}
		} else {
			logger.warn("JWT 토큰이 전달자 문자열로 시작하지 않습니다.");
		}

		// 토큰을 받으면 유효성을 검사합니다.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			// 토큰이 유효한 경우 수동으로 인증을 설정하도록 Spring Security를 ​​구성하십시오.
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//컨텍스트에서 인증을 설정한 후 다음을 지정합니다. 현재 사용자가 인증되었음을 나타냅니다. 그래서 통과한다.
				//Spring 보안 구성에 성공했습니다.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
