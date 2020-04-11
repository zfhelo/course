package org.gdpi.course;

import org.gdpi.course.entity.Course;
import org.gdpi.course.mapper.CourseMapper;
import org.gdpi.course.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@SpringBootTest
class CourseApplicationTests {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Test
    void contextLoads() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper m = new MimeMessageHelper(mimeMessage);
        m.setFrom(javaMailSender.getUsername());
        String[] s = {"zfhelo@gmail.com", "1623602510@qq.com"};
        m.setTo(s);
        m.setSubject("测试");
        m.setText("<h1>aaa</h1>b");
        javaMailSender.send(m.getMimeMessage());
    }

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private TeacherService teacherService;
    @Test
    public void addClass() {
        for (int j = 1; j < 11; j++) {
            for (int i = 0; i < 18; i++) {
                Course c = new Course();
                c.setName("课程"+i);
                c.setTeaId(j);
                c.setNumber("cccccc"+j+i);
                c.setCover("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2462524992,179895772&fm=26&gp=0.jpg");
                c.setDescription("这是一门课程");
                courseMapper.addCourse(c);
            }
        }

    }

    @Resource
    PasswordEncoder passwordEncoder;
    @Test
    public void addSingle() {
        for (int i = 0; i < 10; i++) {
            System.out.print( i+ "----->       ");
            System.out.println(passwordEncoder.encode("" + i + i + i + i + i + i));
        }
    }
    @Test
    public void f() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt());
        }
    }
}
