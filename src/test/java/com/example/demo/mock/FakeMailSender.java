package com.example.demo.mock;

import com.example.demo.user.service.port.MailSender;
import lombok.Getter;

@Getter
public class FakeMailSender implements MailSender {

    private String to;
    private String subject;
    private String text;

    @Override
    public void send(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}