package com.lxc.exception;

/**
 * 邮件发送异常
 */
public class FailedSendingEmailException extends Exception {

    public FailedSendingEmailException(String message) {
        super(message);
    }
}
