package com.example.userapi.application;

import static com.example.userapi.exception.ErrorCode.LOGIN_CHECK_FAIL;

import com.example.domain.common.UserType;
import com.example.domain.config.JwtAuthenticationProvider;
import com.example.userapi.domain.SignInForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.domain.model.Seller;
import com.example.userapi.exception.CustomException;
import com.example.userapi.service.customer.CustomerService;
import com.example.userapi.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;

    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능여부 확인
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        // 2. 토큰 발행 후

        // 3. 토근 response
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        // 1. 로그인 가능여부 확인
        Seller s = sellerService.findValidSeller(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        // 2. 토큰 발행 후

        // 3. 토근 response
        return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
    }

}
