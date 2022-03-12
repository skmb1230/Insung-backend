package com.jwtLogin.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
* @packageName 	: com.jwtLogin.config
* @fileName 	: JwtTokenUtil.java
* @author 		: 이승원
* @date 		: 2022.03.12
* @description 	: JWT를 생성하고 검증하는 역할 수행
* 				  io.jsonwebtoken.Jwts 라이브러리 사용
* ===========================================================
* DATE 			AUTHOR 		NOTE
* -----------------------------------------------------------
* 2022.03.12 	이승원 	최초 생성
*/
@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    
    @Value("${spring.jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    // jwt token으로부터 username을 획득한다.
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    // jwt token으로부터 만료일자를 알려준다.
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    //토큰에서 정보를 검색하려면 비밀 키가 필요합니다.
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    // 토큰이 만료되었는지 확인한다.
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    // 유저를 위한 토큰을 발급해준다.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //토큰을 생성하는 동안 -
	//1. 발급자, 만료, 제목 및 ID와 같은 토큰의 클레임 정의
	//2. HS512 알고리즘과 비밀 키를 사용하여 JWT에 서명합니다.
	//3. JWS Compact 직렬화에 따르면(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   JWT를 URL 안전 문자열로 압축
    /** Token 생성
     * claim : Token에 담을 정보
     * issuer : Token 발급자
	 * subject : Token 제목
	 * issuedate : Token 발급 시간
	 * expiration : Token 만료 시간
	 * milliseconds 기준!
	 * JWT_TOKEN_VALIDITY = 5 60 60 => 5시간
	 * signWith (알고리즘, 비밀키)
     **/
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            //.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
}
