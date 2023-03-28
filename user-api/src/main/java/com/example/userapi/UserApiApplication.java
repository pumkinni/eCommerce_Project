package com.example.userapi;

import com.example.userapi.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
public class UserApiApplication {

//	private final EmailSendService emailSendService;

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}

}
