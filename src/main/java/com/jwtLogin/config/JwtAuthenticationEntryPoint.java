package com.jwtLogin.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
* @packageName 	: com.jwtLogin.config
* @fileName 	: JwtAuthenticationEntryPoint.java
* @author 		: 이승원
* @date 		: 2022.03.12
* @description 	: 허가되지 않은 사용자라면, 접근 불가 메세지를 띄워 리소스 정보획득을 못하게 막아줍니다.
* 				  접근 권한이 없는 사용자에게 401 Error를 보냄
* ===========================================================
* DATE 			AUTHOR 		NOTE
* -----------------------------------------------------------
* 2022.03.12 	이승원 	최초 생성
*/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 5983382933674710346L;

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
