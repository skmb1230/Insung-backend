package com.isl.login.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginVO {
    
    private String userEmail;
    private String userPw;
    private String userName;
    private String userAddr;
    private String userLolId;
    private String nowRank;
    private String highRank;

}
