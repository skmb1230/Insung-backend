package com.jwtLogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	private JwtInterceptor jwtInterceptor;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	
	public WebSecurityConfig(JwtInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}

//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//		System.out.println(">>> 인터셉터 등록22222222");
//		registry.addMapping("/**")
//        .allowedMethods("*")
//        .allowedOriginPatterns("*");
//    }
//	스프링 시큐리티
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println(">>> 인터셉터 등록");
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**");
	}
}
