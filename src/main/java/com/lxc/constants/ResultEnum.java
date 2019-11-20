package com.lxc.constants;

public enum ResultEnum {

    SUCCESS(0, "success"),
    FAIL(1, "fail");

    private ResultEnum(Integer code, String msg) {
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
