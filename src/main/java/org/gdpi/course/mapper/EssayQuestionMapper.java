package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.EssayQuestion;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface EssayQuestionMapper {
    /**
     * 通过id查找题目
     * @param id
     * @return
     */
    EssayQuestion findById(Integer id);

    /**
     * 通过id 和 课程id查找
     * @param id
     * @param courseId
     */
    EssayQuestion findByIdAndCourseId(@Param("id") Integer id, @Param("cid") Integer courseId);


    /**
     * 通过课程id查找
     * @param courseId
     * @return
     */
    List<EssayQuestion> findByCourseId(Integer courseId);

    /**
     * 添加题目
     * @param essayQuestion
     * @return
     */
    Integer addEssayQuestion(EssayQuestion essayQuestion);

    /**
     * 通过id更新题目
     * @param essayQuestion
     * @return
     */
    Integer updateById(EssayQuestion essayQuestion);

    /**
     * 通过 课程id删除题目
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 查找<=指定分数的题目
     * @param grade
     * @return
     */
    List<EssayQuestion> findGradeLessThan(@Param("grade") Integer grade, @Param("cid") Integer cid);

    /**
     * 查找题目被试卷使用的情况
     * @param id
     * @return
     */
    Integer findReferenceNum(Integer id);

    /**
     * 通过试卷id查找所有选择题
     * @param pid
     * @return
     */
    List<EssayQuestion> findByPid(Integer pid);
}
