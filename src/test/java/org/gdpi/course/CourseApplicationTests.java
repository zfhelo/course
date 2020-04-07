package org.gdpi.course;

import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.SingleQuestion;
import org.gdpi.course.mapper.CourseMapper;
import org.gdpi.course.mapper.SingleQuestionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
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

    @Resource
    private CourseMapper courseMapper;
    @Test
    public void addCourse() {
        for (int i = 101; i <= 2000; i++) {

            Course course = new Course();
            course.setTeaId(1);
            course.setCover("xx");
            course.setName("数据结构");
            course.setNumber(i+"");
            courseMapper.addCourse(course);
        }
    }

    @Resource
    private SingleQuestionMapper singleQuestionMapper;
    @Test
    public void addSingleQue() {
        for (int i = 1; i < 2000; i++) {
            SingleQuestion singleQuestion = new SingleQuestion();
            singleQuestion.setCourseId(3);
            singleQuestion.setChoose1("a" + i);
            singleQuestion.setChoose2("b" + i);
            singleQuestion.setChoose3("c" + i);
            singleQuestion.setChoose4("d" + i);
            singleQuestion.setTitle("xxxxxxxxxxxxxx" + i);
            singleQuestion.setGrade(10);
            singleQuestionMapper.addSingleQue(singleQuestion);
        }
    }

}
