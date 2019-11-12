package com.lxc.constants;

public enum BookStatus {

    AVAILABLE(1,"AVAILABLE"), WITHDRAW(2,"WITHDRAW");

    private BookStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
