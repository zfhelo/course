package org.gdpi.course.test.controller;

import org.gdpi.course.service.ValidationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author zhf
 */
@SpringBootTest
public class ValidationCodeServiceTest {
    @Resource
    private ValidationCodeService validationCodeService;

    @Test
    public void TestSendEmailCode() throws MessagingException {
        validationCodeService.sendEmailCode("zfhelo@gmail.com");
    }

    @Test
    public void Testf(@Qualifier(value = "passwordEncoder") PasswordEncoder passwordEncoder) {
        System.out.println(passwordEncoder.encode("123456"));
    }
}
