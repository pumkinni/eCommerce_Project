package com.example.domain.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Aes256UtilTest {


    @Test
    void encrypt() {
        //given
        //when
        //then
        String encrypt = Aes256Util.encrypt("Hello world");
        System.out.println(encrypt);
        assertEquals(Aes256Util.decrypt(encrypt), "Hello world");

    }


}