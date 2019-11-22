package com.lxc.constants;

public enum OrderStatusEnum {

    /**
     * 订单状态-未支付
     */
    UNPAID("UNPAID"),

    /**
     * 订单状态-已支付
     */
    PAID("PAID"),

    /**
     * 订单状态-已取消
     */
    CANCELLED("CANCELLED");

    OrderStatusEnum(String msg) {
        this.msg = msg;
    }

    private final String msg;

    public String getMsg() {
        return msg;
    }
}
