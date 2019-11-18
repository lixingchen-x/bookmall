package com.lxc.constants;

public enum BookStatusEnum {

    AVAILABLE("AVAILABLE"), // 商品状态-在售中
    WITHDRAW("WITHDRAW");  // 商品状态-已下架

    private BookStatusEnum(String msg) {
        this.msg = msg;
    }

    private final String msg;

    public String getMsg() {
        return msg;
    }
}
