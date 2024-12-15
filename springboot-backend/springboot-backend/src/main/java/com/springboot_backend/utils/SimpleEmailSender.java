package com.springboot_backend.utils;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SimpleEmailSender{

    @Autowired
    JavaMailSender mailSender;

    public void sendCode(String receiverEmail, String code) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(receiverEmail));
                mimeMessage.setFrom(new InternetAddress("876320233@qq.com"));
                mimeMessage.setSubject("商品比价平台验证码");
                mimeMessage.setText("亲爱的 " + receiverEmail + ", 感谢您的使用。 " +
                        "您的验证码是 " + code + "。");
            }
        };

        try {
            this.mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    public void sendMessage(String receiverEmail, String subject, String message) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(receiverEmail));
                mimeMessage.setFrom(new InternetAddress("876320233@qq.com"));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);
            }
        };
        try {
            this.mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
