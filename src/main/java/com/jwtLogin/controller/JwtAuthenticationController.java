package com.jwtLogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtLogin.config.JwtTokenUtil;
import com.jwtLogin.service.JwtUserDetailsService;
import com.jwtLogin.vo.JwtRequest;
import com.jwtLogin.vo.JwtResponse;

/**
* @packageName 	: com.jwtLogin.controller
* @fileName 	: JwtAuthenticationController.java
* @author 		: 이승원
* @date 		: 2022.03.12
* @description 	: 사용자가 입력한 id, pw를 body에 넣어서 POST API mapping /authenticate
* 				  사용자의 id, pw를 검증
* 				  jwtTokenUtil을 호출해 Token을 생성하고 JwtResponse에 Token을 담아 return ResponseEntity
* ===========================================================
* DATE 			AUTHOR 		NOTE
* -----------------------------------------------------------
* 2022.03.12 	이승원 	최초 생성
*/

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
	
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
	
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
	
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			//검증매니저 왜오류나는지 이해불가..
			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			System.out.println(e);
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
}
