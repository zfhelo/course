package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Student;
import org.gdpi.course.exception.UserAlreadyExistedException;
import org.gdpi.course.mapper.StudentMapper;
import org.gdpi.course.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public Student findByUsername(String username) {
        return studentMapper.findByUsername(username);
    }


    @Override
    public void addStudent(Student student) throws UserAlreadyExistedException {
        if (findByUsername(student.getUsername()) != null) {
            throw new UserAlreadyExistedException("该账号已被注册");
        }

        // 密码加密
        String encode = passwordEncoder.encode(student.getPassword());
        student.setPassword(encode);

        studentMapper.addStudent(student);
    }

    @Override
    public List<Course> findCourseById(Integer id) {
        return studentMapper.findCourseBySId(id);
    }

    @Override
    public List<Course> findCourse(String key, Integer sid) {
        return studentMapper.findCourse(key, sid);
    }

    @Override
    public Integer findBySidAndCourseId(Integer sid, Integer cid) {
        return studentMapper.findBySidAndCourseId(sid, cid);
    }

    @Override
    public Integer insertJoinCourse(Integer sid, Integer cid) {
        return studentMapper.insertJoinCourse(sid, cid);
    }

    @Override
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }
}
