package org.gdpi.course.service;

import org.gdpi.course.entity.Course;
import org.gdpi.course.exception.CourseAlreadyExistedException;

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
     * 通过教师id查找课程
     * @param teaId
     * @param id
     * @return
     */
    Course findByIdAndTeaId(Integer id, Integer teaId);

    /**
     * 通过 课程id删除课程
     * @param id 课程id
     * @param teaId 教师id
     * @return
     */
    Integer deleteByIdAndTeaId(Integer id, Integer teaId);

    /**
     * 修改课程
     * @param course
     * @return
     */
    Integer updateCourse(Course course);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    Course findById(Integer id);

    /**
     * 查询已发布的作业
     * @param sid
     * @return
     */
    List<Course> findHomework(Integer sid);
}
