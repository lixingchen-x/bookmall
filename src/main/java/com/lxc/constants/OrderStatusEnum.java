package com.lxc.constants;

public enum OrderStatusEnum {

    UNPAID("UNPAID"),  // 订单状态-未支付
    PAID("PAID"),     // 订单状态-已支付
    CANCELLED("CANCELLED"); // 订单状态-已取消

    OrderStatusEnum(String msg) {
        this.msg = msg;
    }

    private final String msg;

    public String getMsg() {
        return msg;
    }
}
