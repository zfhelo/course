package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.exception.CourseAlreadyExistedException;
import org.gdpi.course.mapper.CourseMapper;
import org.gdpi.course.mapper.TeacherMapper;
import org.gdpi.course.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {


    @Resource
    private CourseMapper courseMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Override
    public Integer addCourse(Course course, String username)  throws CourseAlreadyExistedException {

        if (findByNumber(course.getNumber()) != null) {
            throw new CourseAlreadyExistedException();
        }

        Teacher tea = teacherMapper.findByUsername(username);
        course.setTeaId(tea.getId());

        return courseMapper.addCourse(course);
    }

    @Override
    public Course findByNumber(String number) {
        return courseMapper.findByNumber(number);
    }

    @Override
    public List<Course> findByTeaId(Integer teaId) {
       return courseMapper.findByTeaId(teaId);
    }

    @Override
    public Integer deleteByIdAndTeaId(Integer id, Integer teaId) {
       return courseMapper.deleteByIdAndTeaId(id, teaId);
    }

    @Override
    public Course findByIdAndTeaId(Integer id, Integer teaId) {
        return courseMapper.findByIdAndTeaId(id, teaId);
    }

    @Override
    public Integer updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }
}
