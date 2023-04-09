package com.ldh.crm.vo;

import lombok.Data;

@Data
public class UserRegister {

    private String nickname;
    private String email;
    private String password;
    private String checkPass;
    private String code;
}
