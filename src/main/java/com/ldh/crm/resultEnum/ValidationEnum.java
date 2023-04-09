package com.ldh.crm.resultEnum;


public enum ValidationEnum {
    REGISTER(1),FORGET_PASS(2);

    private Integer code;

    ValidationEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
