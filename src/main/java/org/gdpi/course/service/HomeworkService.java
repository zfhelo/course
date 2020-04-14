package org.gdpi.course.service;

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
}
