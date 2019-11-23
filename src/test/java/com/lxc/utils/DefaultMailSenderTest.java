package com.lxc.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.MessagingException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMailSenderTest {

    @InjectMocks
    private DefaultMailSender defaultMailSender;

    @Mock
    private MailSender sender;

    @Test
    public void send_happyPath() throws MessagingException {

        defaultMailSender.send("511082291@qq.com", "a");

        verify(sender).sendMail("511082291@qq.com", "a", "图书商城官方邮件");
    }

    @Test(expected = MessagingException.class)
    public void send_shouldThrowException_ifMessageWentWrong() throws MessagingException {

        doThrow(MessagingException.class).when(sender).sendMail("511082291@qq.com", "a", "图书商城官方邮件");

        defaultMailSender.send("511082291@qq.com", "a");
    }
}
