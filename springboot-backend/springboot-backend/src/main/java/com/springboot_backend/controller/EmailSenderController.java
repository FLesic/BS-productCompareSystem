package com.springboot_backend.controller;

import com.springboot_backend.Response;
import com.springboot_backend.service.EmailService;
import com.springboot_backend.utils.SimpleEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSenderController {
    @Autowired
    EmailService emailService;

    @GetMapping("/mail/sendCode")
    public Response<String> sendMail(@RequestParam("receiver") String receiver) {
        try{
            String code = Double.toString(Math.random()).substring(2, 8);
            emailService.sendCode(receiver, code);
            return Response.newSuccess(code);
        } catch (Exception e) {
            return Response.newFail(e.getMessage());
        }
    }

    //TODO降价提醒邮箱
}
