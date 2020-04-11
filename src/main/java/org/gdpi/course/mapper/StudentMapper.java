package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Student;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface StudentMapper {
    /**
     * 创建用户
     * @param student
     * @return 影响记录数
     */
    Integer addStudent(Student student);

    /**
     * 删除用户
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 通过账号查找用户
     * @param username
     * @return null or user
     */
    Student findByUsername(String username);

    /**
     * 通过id 查找选的课程
     * @param id
     * @return
     */
    List<Course> findCourseBySId(Integer id);

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
}
