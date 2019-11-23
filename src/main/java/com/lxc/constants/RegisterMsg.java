package com.lxc.constants;

public enum RegisterMsg {

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(0),

    /**
     * 密码少于6位
     */
    PASSWORD_SHORT(1),

    /**
     * 注册成功
     */
    REGISTER_SUCCESS(2);

    RegisterMsg(Integer code) {

        this.code = code;
    }

    private final Integer code;

    public Integer getCode(){
        return code;
    }
}
