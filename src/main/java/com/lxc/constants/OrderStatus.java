package com.lxc.constants;

public enum OrderStatus {

    UNPAID(1,"UNPAID"), PAID(2,"PAID"), CANCELLED(3,"CANCELLED");

    private OrderStatus(Integer code, String msg) {
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
