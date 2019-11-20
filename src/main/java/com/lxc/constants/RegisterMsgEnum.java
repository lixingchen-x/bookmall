package com.lxc.constants;

public enum RegisterMsgEnum {

    USERNAME_EXISTS(0, "USERNAME_EXISTS"),
    PASSWORD_SHORT(1, "PASSWORD_SHORT"),
    REGISTER_SUCCESS(2,"REGISTER_SUCCESS");

    private RegisterMsgEnum(Integer code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
