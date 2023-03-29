package com.example.userapi.application;


import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.domain.model.Seller;
import com.example.userapi.exception.CustomException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.client.MailgunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import com.example.userapi.service.customer.SignUpCustomerService;
import com.example.userapi.service.seller.SellerService;
import com.example.userapi.service.seller.SignUpSellerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester1234@email.com")
                .to(form.getEmail())
                .subject("verification Email!")
                .text(getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code))
                .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);

            return "회원가입에 성공하였습니다.";
        }
    }


    public void sellerVerify(String email, String code) {
        signUpSellerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm form) {
        if (signUpSellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        } else {
            Seller s = signUpSellerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester1234@email.com")
                .to(form.getEmail())
                .subject("verification Email!")
                .text(getVerificationEmailBody(s.getEmail(), s.getName(), "seller", code))
                .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpSellerService.changeSellerValidateEmail(s.getId(), code);

            return "회원가입에 성공하였습니다.";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name)
            .append("! Please Click Link for verification\n\n")
            .append("http://localhost:8081/signup/"+type+"/verify?email=")
            .append(email)
            .append("&code=")
            .append(code)
            .toString();
    }


}
