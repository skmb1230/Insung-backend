package com.jwtLogin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

	/**
	 * 
	 * @param authenticationRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,HttpServletResponse response) throws Exception {
		
		//authenticate(authenticationRequest.getUserEmail(), authenticationRequest.getPassword());
		System.out.println("id ::"+authenticationRequest.getUserEmail());
		System.out.println("pw ::"+authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUserEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private void authenticate(String username, String password) throws Exception {
        try {
        	
        	Authentication authentication = authenticationManager.authenticate(
    				new UsernamePasswordAuthenticationToken(username, password));
            System.out.println(authentication);
        } catch (DisabledException e) {
        	System.out.println(e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
        	System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
	
	// Refresh API
	@RequestMapping(value="/refreshToken" , method=RequestMethod.POST)
	public String refreshToken(HttpServletRequest request , HttpServletResponse response) throws Exception{
	 
	    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
	    System.out.println("만료안되길");
	    return token.substring(7);
	}
	
}
