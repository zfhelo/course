package org.gdpi.course.service;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Homework;
import org.gdpi.course.entity.StudentHomework;

import java.util.List;

/**
 * @author zhf
 */
public interface HomeworkService {
    /**
     * 发布作业
     * @param homework
     * @return
     */
    Integer addHomeWork(Homework homework);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    List<Homework> findByCourseId(Integer id);

    /**
     * 删除作业
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    Homework findById(Integer id);

    /**
     * 通过作业id查找所有学员的作业
     * @param id
     * @return
     */
    List<StudentHomework> findByHomeworkId(Integer id);

    /**
     * 通过id查找作业
     * @param id
     * @return
     */
    StudentHomework findStuHomeworkById(Integer id);

    /**
     * 修改成绩
     * @param studentHomework
     */
    Integer updateGradeById(StudentHomework studentHomework);

    /**
     * 通过课程号查询已发布的作业
     * @param cid
     * @return
     */
    List<Homework> findByCid(Integer cid);


    /**
     * 查找学生指定课程已提交的作业
     * @param cid
     * @param sid
     * @return
     */
    List<Homework> findSubmit(Integer cid, Integer sid);

    /**
     * 查找已过期的
     * @param cid
     * @param sid
     * @return
     */
    List<Homework> findOverdue(Integer cid, Integer sid);

    /**
     * 通过课程号和学号查作业
     * @param cid
     * @param sid
     * @return
     */
    List<Homework> findByCourseIdForStu(Integer cid, Integer sid);

    /**
     *
     * @param hid
     * @param sid
     * @return
     */
    StudentHomework findByHomeworkIdAndStuId(@Param("hid") Integer hid, @Param("sid") Integer sid);

    /**
     * 更新作业
     * @param studentHomework
     * @return
     */
    Integer updateHomework(StudentHomework studentHomework);

    /**
     * 删除该学员在该课程下所有作业
     * @param cid
     * @param sid
     */
    void deleteByAllForStu(Integer cid, Integer sid);

    /**
     * 查询所有选课的作业
     * @param courses
     * @param sid
     */
    void findAllHomework(List<Course> courses, Integer sid);


    /**
     * 查找所有作业
     * @param cid
     * @return
     */
    List<Homework> findByHomeworkByCid(Integer cid);
}
