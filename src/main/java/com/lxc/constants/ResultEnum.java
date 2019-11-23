package com.lxc.constants;

public enum ResultEnum {

    /**
     * 成功码
     */
    SUCCESS(0),

    /**
     * 失败码
     */
    FAIL(1);

    ResultEnum(Integer code) {
        this.code = code;
    }

    private final Integer code;

    public Integer getCode(){
        return code;
    }
}
