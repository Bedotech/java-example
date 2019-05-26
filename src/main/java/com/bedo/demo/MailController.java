package com.bedo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@RestController
public class MailController {
    private final JavaMailSender javaMailSender;

    @Autowired
    MailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping("/mail")
    public boolean mail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("corso@sia.it");
        message.setTo("cani@padroni.it");
        message.setSubject("Corso");
        message.setText("Questa email e molto bella!");
        this.javaMailSender.send(message);
        return true;
    }
}
