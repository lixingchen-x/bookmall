package com.lxc.constants;

public enum ResultEnum {

    /**
     * 成功码
     */
    SUCCESS(0, "success"),
    /**
     * 失败码
     */
    FAIL(1, "fail");

    ResultEnum(Integer code, String msg) {
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
