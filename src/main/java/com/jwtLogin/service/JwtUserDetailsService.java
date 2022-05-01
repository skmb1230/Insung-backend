package com.jwtLogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwtLogin.dao.JwtDao;


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

	@Autowired()
	public JwtDao jwtDao;
	 
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
	
		JwtUserDetails user = jwtDao.selectUserById(userEmail);
		if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userEmail);
        }
		
		return user;
	
	}
	
}
