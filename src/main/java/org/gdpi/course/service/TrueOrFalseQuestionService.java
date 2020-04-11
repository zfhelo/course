package org.gdpi.course.service;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.TrueOrFalseQuestion;

import java.util.List;

/**
 * @author zhf
 */
public interface TrueOrFalseQuestionService {
    /**
     * 通过id查找题目
     * @param id
     * @return
     */
    TrueOrFalseQuestion findById(Integer id);

    /**
     * 通过id 和 课程id查找
     * @param id
     * @param courseId
     */
    TrueOrFalseQuestion findByIdAndCourseId(@Param("id") Integer id, @Param("cid") Integer courseId);


    /**
     * 通过课程id查找
     * @param courseId
     * @return
     */
    List<TrueOrFalseQuestion> findByCourseId(Integer courseId);

    /**
     * 添加题目
     * @param trueOrFalseQuestion
     * @return
     */
    Integer addTrueOrFalseQuestion(TrueOrFalseQuestion trueOrFalseQuestion);

    /**
     * 通过id更新题目
     * @param trueOrFalseQuestion
     * @return
     */
    Integer updateById(TrueOrFalseQuestion trueOrFalseQuestion);

    /**
     * 通过 课程id删除题目
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 查找题目被试卷使用的情况
     * @param id
     * @return
     */
    Integer findReferenceNum(Integer id);
}
