package com.lxc.constants;

public enum BookStatusEnum {

    /**
     * 商品状态-在售中
     */
    AVAILABLE("AVAILABLE"),

    /**
     * 商品状态-已下架
     */
    WITHDRAW("WITHDRAW");

    BookStatusEnum(String msg) {
        this.msg = msg;
    }

    private final String msg;

    public String getMsg() {
        return msg;
    }
}
