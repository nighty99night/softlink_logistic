package ru.kalinichenko.softlink_logistic.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
public class EmailNotificationService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;
    public EmailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailNotification(String recipientEmail, String subject, String content) {
        //OLD
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);*/


        //NEW
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
// *** BEGIN CHANGE
        properties.put("mail.smtp.user", from);

        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getDefaultInstance(properties);
// *** END CHANGE

        // creates a new e-mail message
        Message msg = new MimeMessage(session);


        InternetAddress[] toAddresses;
        try {
            msg.setFrom(new InternetAddress(from));
            toAddresses = new InternetAddress[]{ new InternetAddress(recipientEmail) };
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(content);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        // set plain text message


// *** BEGIN CHANGE
        // sends the e-mail


        try {
            Transport t = session.getTransport("smtp");
            t.connect(from, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

// *** END CHANGE
    }


}

