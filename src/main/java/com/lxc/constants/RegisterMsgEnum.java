package com.lxc.constants;

public enum RegisterMsgEnum {

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(0, "USERNAME_EXISTS"),

    /**
     * 密码少于6位
     */
    PASSWORD_SHORT(1, "PASSWORD_SHORT"),

    /**
     * 注册成功
     */
    REGISTER_SUCCESS(2,"REGISTER_SUCCESS");

    RegisterMsgEnum(Integer code, String msg) {

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
