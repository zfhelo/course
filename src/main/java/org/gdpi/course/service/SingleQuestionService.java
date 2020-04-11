package org.gdpi.course.service;

import org.gdpi.course.entity.SingleQuestion;

import java.util.List;

/**
 * @author zhf
 */
public interface SingleQuestionService {
    /**
     * 添加题目
     * @param singleQuestion
     * @return
     */
    Integer addSingleQue(SingleQuestion singleQuestion);

    /**
     * 通过id和课程id查找题库
     * @param id
     * @param cid
     * @return
     */
   SingleQuestion findByIdAndCourseId(Integer id, Integer cid);

    /**
     * 通过id查找题目
     * @param cid
     * @return
     */
   List<SingleQuestion> findByCourseId(Integer cid);

    /**
     * 通过id查找题目
     * @param id
     * @return
     */
   SingleQuestion findById(Integer id);

    /**
     * 通过id删除题目
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 修改题目
     * @param singleQuestion
     * @return
     */
    Integer updateSingleQuestion(SingleQuestion singleQuestion);

    /**
     * 查找题目被试卷使用的情况
     * @param id
     * @return
     */
    Integer findReferenceNum(Integer id);
}
