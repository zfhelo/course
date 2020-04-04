package org.gdpi.course.controller;

import org.gdpi.course.service.ValidationCodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@RestController
@SessionAttributes("code")
public class ValidationController {

    @Resource
    private ValidationCodeService validationCodeService;

    @ResponseBody
    @PostMapping("/code")
    public String emailCode(@RequestBody String email) {
        //EmailCode emailCode = EmailCode.simpleMaleCode(email);
        //emailCode.setCode(RandomStringUtils.randomAlphabetic(6));
        //emailCode.setMessage("验证码为:" + emailCode.getCode());
        return null;
    }

}
