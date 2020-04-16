package org.gdpi.course.service;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Student;
import org.gdpi.course.exception.UserAlreadyExistedException;

import java.util.List;

/**
 * @author zhf
 */
public interface StudentService {

    /**
     * 创建用户
     * @param student
     * @throws UserAlreadyExistedException
     */
    void addStudent(Student student) throws UserAlreadyExistedException;

    /**
     * 通过账号查找用户
     * @param username
     * @return
     */
    Student findByUsername(String username);

    /**
     * 通过学生id查询所选的课程
     * @param id
     * @return
     */
    List<Course> findCourseById(Integer id);

    /**
     * 模糊查找学生没有加入的课程
     * @param key 查询条件 课程名or课程号 参数需要加 %%
     * @param sid 学生id
     * @return
     */
    List<Course> findCourse(@Param("key") String key, @Param("sid") Integer sid);



    /**
     * 通过学生好喝课程号查询是否选课
     * @param sid
     * @param cid
     * @return
     */
    Integer findBySidAndCourseId(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /**
     * 加入课程
     * @param sid
     * @param cid
     * @return
     */
    Integer insertJoinCourse(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /**
     * 通过id查找学生
     * @return
     */
    Student findById(Integer id);

    /**
     * 初始化课程页
     * @param sid
     * @return
     */
    List<Course> initExamIndex(Integer sid);

    /**
     * 更新用户
     * @param student
     */
    void updateById(Student student);
}

