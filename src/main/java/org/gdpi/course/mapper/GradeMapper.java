package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.GradeTable;

import java.util.List;

/**
 * @author zhf
 */
public interface GradeMapper {

    /**
     * 查找该课程成绩
     * @param cid 课程id
     * @param sid 学生id
     * @return
     */
    GradeTable findGradeTable(@Param("cid") Integer cid, @Param("sid") Integer sid);


    /**
     * 修改成绩
     * @param gradeTable
     * @return
     */
    Integer updateGradeTable(GradeTable gradeTable);

    /**
     * 通过课程号查成员的成绩表
     * @param id
     * @return
     */
    List<GradeTable> findByCourseId(Integer id);

    /**
     * 通过id查找成绩表
     * @param id
     * @return
     */
    GradeTable findById(Integer id);

    /**
     * 删除学员
     * @param id
     * @return
     */
    Integer deleteById(Integer id);
}
