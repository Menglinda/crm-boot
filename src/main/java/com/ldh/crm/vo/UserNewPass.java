package com.ldh.crm.vo;

import lombok.Data;

@Data
public class UserNewPass {

    private String email;
    private String password;
    private String checkPass;
    private String code;
}
