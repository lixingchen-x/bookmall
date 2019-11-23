package com.lxc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class DefaultMailSender {

    @Autowired
    private MailSender mailSender;

    public void send(String email, String msg) throws MessagingException {

        String defaultSubject = "图书商城官方邮件";
        mailSender.sendMail(email, msg, defaultSubject);
    }
}
