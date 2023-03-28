package com.example.userapi.service;

import com.example.userapi.client.MailgunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    @Value("${mailgun.mail}")
    private String mailTo;

    public String sendEmail() {
        SendMailForm form = SendMailForm.builder()
                .from("zorobase-test@dannyEmail.com")
                .to(mailTo)
                .subject("test send mail3")
                .text("second mail")
                .build();

        return mailgunClient.sendEmail(form).getBody();
    }
}
