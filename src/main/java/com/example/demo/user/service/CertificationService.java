package com.example.demo.user.service;

import com.example.demo.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String to, String subject, String text) {
        mailSender.send(to, subject, text);
    }
}
