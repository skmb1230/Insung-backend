package com.isl.login;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.isl.login.service.LoginService;
import com.isl.login.vo.LoginVO;

@RestController
public class LoginController {
       
    @Autowired 
    LoginService loginService; 

    @RequestMapping(value = "/login")
    public String loginTest() throws Exception { 

         Logger logger = LoggerFactory.getLogger(LoginController.class);
         ModelAndView mav = new ModelAndView("index"); 

         List<LoginVO> testList = loginService.selectLoginTest(); 

         logger.error("test ::  "+ testList);

         mav.addObject("list", testList); 
         
         return "Hello world"; 
         
    }
    
    
}
