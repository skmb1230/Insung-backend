package com.jwtLogin.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
* @packageName 	: com.jwtLogin.service
* @fileName 	: JwtUserDetailsService.java
* @author 		: 이승원
* @date 		: 2022.03.12
* @description 	: UserDetailsService를 implements 해주었다는 것이 중요합니다.
* 				  DB에서 UserDetail를 얻어와 AuthenticationManager에게 제공하는 역할을 수행합니다.
* ===========================================================
* DATE 			AUTHOR 		NOTE
* -----------------------------------------------------------
* 2022.03.12 	이승원 	최초 생성
*/

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		if ("skmb1230".equals(username)) {
            return new User("skmb1230", "$2a$10$m/enYHaLsCwH2dKMUAtQp.ksGOA6lq7Fd2pnMb4L.yT4GyeAPRPyS",
                new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
	
	}
	
}
