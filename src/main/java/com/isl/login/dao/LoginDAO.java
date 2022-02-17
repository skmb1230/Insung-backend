package com.isl.login.dao;

import java.util.List;

import javax.annotation.Resource;

import com.isl.login.vo.LoginVO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO{
    
    @Resource
    private SqlSessionTemplate sqlSession;

    public List<LoginVO> selectLoginTest() {
  
        return sqlSession.selectList("loginDAO.selectLoginTest", null);

    }
}
