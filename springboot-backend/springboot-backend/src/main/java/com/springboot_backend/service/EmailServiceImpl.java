package com.springboot_backend.service;

import com.springboot_backend.utils.SimpleEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    SimpleEmailSender simpleEmailSender;

    @Override
    public void sendCode(String receiverEmail, String code) {
        simpleEmailSender.sendCode(receiverEmail, code);
    }

    @Override
    public void sendMessage(String receiverEmail, String subject, String message) {
        simpleEmailSender.sendMessage(receiverEmail, subject, message);
    }
}
