package com.example.userapi.service;

import com.example.userapi.config.FeignConfig;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {
    @Autowired
    private EmailSendService emailSendService;

    @Test
    void EmailTest() {
        //given
        //when
        //then
        String response = emailSendService.sendEmail();
        System.out.println(response);
    }
}