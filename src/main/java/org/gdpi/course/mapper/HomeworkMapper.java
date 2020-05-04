package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.Homework;
import org.gdpi.course.entity.StudentHomework;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface HomeworkMapper {

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

    void addHomeworkForStu(@Param("hid") Integer homeworkId, @Param("sid") Integer sid);

    /**
     * 通过id查找提交的人数
     * @param id
     * @return
     */
    Integer countSubmitNum(Integer id);

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
    List<Homework> findSubmit(@Param("cid") Integer cid, @Param("sid") Integer sid);

    /**
     * 查找已过期的
     * @param cid
     * @param sid
     * @return
     */
    List<Homework> findOverdue(@Param("cid") Integer cid, @Param("sid") Integer sid);

    List<Homework> findByCourseIdForStu(@Param("cid") Integer cid, @Param("sid") Integer sid);

    StudentHomework findByHomeworkIdAndStuId(@Param("hid") Integer hid, @Param("sid") Integer sid);

    /**
     * 更新作业
     * @param studentHomework
     * @return
     */
    Integer updateHomework(StudentHomework studentHomework);

    void deleteByAllForStu(@Param("cid") Integer cid, @Param("sid") Integer sid);

    List<StudentHomework> findAllHomework(@Param("cid") Integer cid, @Param("sid") Integer sid);

    List<Homework> findByHomeworkByCid(Integer cid);
}
