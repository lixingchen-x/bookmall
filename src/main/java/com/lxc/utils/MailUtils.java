package com.lxc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtils {

    @Value("${sender.email}")
    private String sender;
    @Value("${sender.auth_code}")
    private String authCode;

    @Value("${property.protocol}")
    private String protocol;
    @Value("${property.protocol-value}")
    private String protocolValue;

    @Value("${property.host}")
    private String host;
    @Value("${property.host-value}")
    private String hostValue;

    @Value("${property.auth}")
    private String auth;
    @Value("${property.auth-value}")
    private String authValue;

    public void sendMail(String email, String msg, String subject) throws MessagingException {

        Properties props = initProperties();

        Authenticator authenticator = setAuthenticator();

        Session session = Session.getInstance(props, authenticator);

        Message message = createMessage(session, email, msg, subject);

        Transport.send(message);
    }

    private Properties initProperties() {

        Properties props = new Properties();
        props.setProperty(protocol, protocolValue);
        props.setProperty(host, hostValue);
        props.setProperty(auth, authValue);
        return props;
    }

    private Authenticator setAuthenticator() {

        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, authCode);
            }
        };
    }

    private Message createMessage(Session session, String email, String msg, String subject) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setContent(msg, "text/html;charset=utf-8");
        return message;
    }
}
