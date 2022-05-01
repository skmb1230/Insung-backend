package com.jwtLogin.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jwtLogin.service.JwtUserDetails;

@Repository
public class JwtDao {

	 @Resource
	 private SqlSessionTemplate sqlSession;
	 
	 public JwtUserDetails selectUserById(String userEmail) {
		  
		 return (JwtUserDetails)sqlSession.selectOne("JwtDao.selectUserById", userEmail);

	 }
}
