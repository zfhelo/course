package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.SingleQuestion;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface SingleQuestionMapper {

    /**
     * 添加课程
     * @param singleQuestion
     * @return 影响条数
     */
    Integer addSingleQue(SingleQuestion singleQuestion);

    /**
     * 通过课程id和教师id查找
     * @param id
     * @param cid
     * @return
     */
    SingleQuestion findByIdAndCourseId(@Param("id") Integer id, @Param("cid") Integer cid);

    List<SingleQuestion> findByCourseId(Integer cid);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    SingleQuestion findById(Integer id);

    /**
     * 删除id
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
     * 查找<=指定分数的题目
     * @param grade
     * @return
     */
    List<SingleQuestion> findGradeLessThan(@Param("grade") Integer grade, @Param("cid") Integer cid);

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
    List<SingleQuestion> findByPid(Integer pid);



}
