package com.lxc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class DefaultMailSender {

    @Autowired
    private MailUtils mailUtils;

    public void send(String email, String msg) throws MessagingException {

        String defaultSubject = "图书商城官方邮件";
        mailUtils.sendMail(email, msg, defaultSubject);
    }
}
