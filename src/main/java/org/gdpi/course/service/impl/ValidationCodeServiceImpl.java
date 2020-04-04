package org.gdpi.course.service.impl;

import lombok.Data;
import org.gdpi.course.entity.EmailCode;
import org.gdpi.course.service.ValidationCodeService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author zhf
 */
@Service
@ConfigurationProperties(prefix = "validation.mail")
@Data
public class ValidationCodeServiceImpl implements ValidationCodeService {
    @Resource
    private JavaMailSenderImpl javaMailSender;
    private String template = "验证码为:{code}  {timeout}分钟内有效";
    private Integer timeout = 10;
    private String subject = "课程管理平台";

    @Override
    public EmailCode sendEmailCode(String to) throws MessagingException {

        EmailCode emailCode = EmailCode.simpleMaleCode(to);
        emailCode.setTimeout(emailCode.getTimeout().plusMinutes(timeout));

        String msg = template.replace("{code}", emailCode.getCode())
                .replace("{timeout}", timeout+"");


        MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage());

        messageHelper.setTo(to);
        messageHelper.setText(msg, true);
        messageHelper.setSubject(subject);
        messageHelper.setFrom(javaMailSender.getUsername());

        javaMailSender.send(messageHelper.getMimeMessage());


        return emailCode;
    }

}
