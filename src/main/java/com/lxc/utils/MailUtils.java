package com.lxc.utils;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtils {

    private static final String SENDER = "lxc305717@163.com";
    private static final String AUTH_CODE = "lxc5178";

    public void sendMail(String email, String msg) throws MessagingException {

        Properties props = initProperties();

        Authenticator authenticator = setAuthenticator();

        Session session = Session.getInstance(props, authenticator);

        Message message = createMessage(session, email, msg);

        Transport.send(message);
    }

    private Properties initProperties() {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.smtp.auth", "true");
        return props;
    }

    private Authenticator setAuthenticator() {

        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, AUTH_CODE);
            }
        };
    }

    private Message createMessage(Session session, String email, String msg) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("图书商城官方邮件");
        message.setContent(msg, "text/html;charset=utf-8");
        return message;
    }
}
