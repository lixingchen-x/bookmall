package com.lxc.exception;

/**
 * 库存不足的自定义异常
 */
public class StockNotEnoughException extends Exception {

    public StockNotEnoughException(String message) {
        super(message);
    }
}
