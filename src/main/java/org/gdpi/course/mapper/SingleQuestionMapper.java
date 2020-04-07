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
}
