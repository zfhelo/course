package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Teacher;
import org.gdpi.course.exception.UserAlreadyExistedException;
import org.gdpi.course.mapper.TeacherMapper;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@Service("teacherService")
@Transactional
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
    public void addTeacher(Teacher teacher) throws UserAlreadyExistedException {

        if (findByUsername(teacher.getUsername()) != null) {
            throw new UserAlreadyExistedException("该账户已被注册");
        }
        // 加密
        String encode = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encode);
        teacherMapper.addTeacher(teacher);
    }

    @Override
    public void updateById(Teacher teacher) {
        teacherMapper.updateById(teacher);
    }
}
