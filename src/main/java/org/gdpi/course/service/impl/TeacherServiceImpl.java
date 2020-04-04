package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Teacher;
import org.gdpi.course.mapper.TeacherMapper;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Teacher findByUsername(String username) {
        return teacherMapper.findByUsername(username);
    }


    @Override
    public void addTeacher(Teacher teacher) {

        // 加密
        String encode = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encode);
        teacherMapper.addTeacher(teacher);
    }
}
