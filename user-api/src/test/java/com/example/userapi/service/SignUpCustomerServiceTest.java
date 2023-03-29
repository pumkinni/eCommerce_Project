package com.example.userapi.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.service.customer.SignUpCustomerService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        //given
        SignUpForm form = SignUpForm.builder()
            .name("name")
            .birth(LocalDate.now())
            .email("abc@naver.com")
            .password("1")
            .phone("01012341234")
            .build();
        Customer c = service.signUp(form);
        //when


        //then
        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
    }

}