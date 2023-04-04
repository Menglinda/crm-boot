package com.ldh.crm.vo;

import lombok.Data;

@Data
public class ChangePsdInfo {
    private String email;
    private String oldPass;
    private String pass;
    private String checkPass;
}
