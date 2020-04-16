package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.Course;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface CourseMapper {
    /**
     * 创建课程
     * @param course
     * @return
     */
    Integer addCourse(Course course);

    /**
     * 通过课程号查找课程
     * @param number 课程号
     * @return null or course
     */
    Course findByNumber(String number);

    /**
     * 通过教师id查找课程
     * @param teaId 教师id
     * @return 该教师的所有课程
     */
    List<Course> findByTeaId(Integer teaId);

    /**
     * 通过教师id和课程id查找课程
     * @param teaId 教师id
     * @param id 课程id
     * @return 该教师的所有课程
     */
    Course findByIdAndTeaId(@Param("id") Integer id, @Param("teaId") Integer teaId);

    /**
     * 通过id删除课程
     * @return 影响条数
     */
    Integer deleteByIdAndTeaId(@Param("id") Integer id, @Param("teaId") Integer teaId);

    /**
     * 修改课程信息
     * @param course
     * @return
     */
    Integer updateCourse(Course course);

    /**
     * 通过课程号查找有多少学员
     * @param cid
     * @return
     */
    Integer findStudentCount(Integer cid);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    Course findById(Integer id);

    /**
     * 查找所有学员id
     * @param cid
     * @return
     */
    List<Integer> findStudentId(Integer cid);

    /**
     * 查询选课的学生
      * @param sid
     * @return
     */
    List<Course> findCourseBySid(Integer sid);


    /**
     * 查询已发布的作业
     * @param sid
     * @return
     */
    List<Course> findHomework(Integer sid);

    /**
     * 通过课程号和学生id查找
     * @param number
     * @param sid
     * @return
     */
    Course findByNumberAndSid( @Param("number") String number, @Param("sid") Integer sid);

}
