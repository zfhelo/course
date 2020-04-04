package org.gdpi.course.service;

import org.gdpi.course.entity.Course;
import org.gdpi.course.exception.CourseAlreadyExistedException;
import org.gdpi.course.exception.CourseNotFoundException;

import java.util.List;

/**
 * @author zhf
 */
public interface CourseService {
    /**
     * 添加课程
     * @param course
     * @param username 教师账号
     * @return 影响条数
     */
    Integer addCourse(Course course, String username) throws CourseAlreadyExistedException;

    /**
     * 通过课程号查找
     * @param number
     * @return
     */
    Course findByNumber(String number);

    /**
     * 通过教师id查找课程
     * @param teaId
     * @return
     */
    List<Course> findByTeaId(Integer teaId);

    /**
     * 通过 课程id删除课程
     * @param id
     * @return
     */
    Integer deleteById(Integer id) throws CourseNotFoundException;
}
