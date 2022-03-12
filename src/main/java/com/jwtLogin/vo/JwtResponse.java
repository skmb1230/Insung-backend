package com.jwtLogin.vo;

import java.io.Serializable;

/**
* @packageName 	: com.jwtLogin.vo
* @fileName 	: JwtResponse.java
* @author 		: 이승원
* @date 		: 2022.03.12
* @description 	: 응답 객체입니다.
* 				  토큰을 반환합니다. 마찬가지로 커스터마이징 가능합니다.
* ===========================================================
* DATE 			AUTHOR 		NOTE
* -----------------------------------------------------------
* 2022.03.12 	이승원 	최초 생성
*/
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -5164344626251330164L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}

}
