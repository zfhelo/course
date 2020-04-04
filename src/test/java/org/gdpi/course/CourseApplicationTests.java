package org.gdpi.course;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootTest
class CourseApplicationTests {

    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Test
    void contextLoads() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper m = new MimeMessageHelper(mimeMessage);
        m.setFrom(javaMailSender.getUsername());
        String[] s = {"zfhelo@gmail.com","1623602510@qq.com"};
        m.setTo(s);
        m.setSubject("测试");
        m.setText("<h1>aaa</h1>b");
        javaMailSender.send(m.getMimeMessage());
    }

}
