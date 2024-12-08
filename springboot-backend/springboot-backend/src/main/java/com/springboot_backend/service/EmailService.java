package com.springboot_backend.service;

public interface EmailService {
    // 发送验证码
    void sendCode(String receiverEmail, String code);

    // 消息发送
    void sendMessage(String receiverEmail, String subject,String message);
}
