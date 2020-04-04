package org.gdpi.course.test.mapper;

import org.gdpi.course.entity.Teacher;
import org.gdpi.course.mapper.TeacherMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@SpringBootTest(args = "--spring.profiles.active=test")
public class TeacherMapperTest {

    @Resource
    TeacherMapper teacherMapper;

    @Test
    public void testCreateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setEmail("xxx");
        teacher.setUsername("854232423463");
        teacher.setPassword("1");
        Assertions.assertEquals(1, teacherMapper.addTeacher(teacher));
    }
}
