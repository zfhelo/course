package org.gdpi.course.service;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.EssayQuestion;

import java.util.List;

/**
 * @author zhf
 */
public interface EssayQuestionService {
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
}
