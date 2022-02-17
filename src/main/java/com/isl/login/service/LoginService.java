package com.isl.login.service;

import java.util.List;

import com.isl.login.dao.LoginDAO;
import com.isl.login.vo.LoginVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired()
    public LoginDAO loginDAO;

    public List<LoginVO> selectLoginTest() {
        
        return loginDAO.selectLoginTest();
        
    }
}
