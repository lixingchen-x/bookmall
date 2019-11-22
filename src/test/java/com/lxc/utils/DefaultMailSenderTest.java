package com.lxc.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMailSenderTest {

    @InjectMocks
    private DefaultMailSender mailSender;

    @Mock
    private MailUtils mailUtils;

    @Test
    public void send_happyPath() throws MessagingException {

        mailSender.send("511082291@qq.com", "a");

        verify(mailUtils).sendMail("511082291@qq.com", "a", "图书商城官方邮件");
    }

    @Test(expected = MessagingException.class)
    public void send_shouldThrowException_ifMessageWentWrong() throws MessagingException {

        doThrow(MessagingException.class).when(mailUtils).sendMail("511082291@qq.com", "a", "图书商城官方邮件");

        mailSender.send("511082291@qq.com", "a");
    }
}
