package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.ExamPaper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface ExamPaperMapper {
    /**
     * 通过模板试卷查找所有试卷
     * @param modelId
     * @return
     */
    List<ExamPaper> findByModelId(Integer modelId);

    /**
     * 查询已提交的试卷
     * @param sid
     * @param cid
     * @return
     */
    List<ExamPaper> findSubmitBySid(@Param("sid") Integer sid, @Param("cid")Integer cid);

    /**
     * 查询该学生在指定课程下的试卷
     * @param sid
     * @return
     */
    List<ExamPaper> findAllBySid(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    ExamPaper findById(Integer id);


    /**
     * 通过试卷id获取选择题
     * @param id
     * @return
     */
    ExamPaper getQuestion(Integer id);

    /**
     * 更新用户答案
     * @param pId 试卷id
     * @param singleId 题目id
     * @param answer 答案
     */
    void updateSingleUserAnswer(@Param("pid") Integer pId, @Param("singleId") Integer singleId, @Param("answer") String answer);

    /**
     * 更新用户答案
     * @param pId 试卷id
     * @param singleId 题目id
     * @param answer 答案
     */
    void updateGapUserAnswer(@Param("pid") Integer pId, @Param("gapId") Integer singleId, @Param("answer") String answer);

    /**
     * 更新用户答案
     * @param pId 试卷id
     * @param singleId 题目id
     * @param answer 答案
     */
    void updateTorfUserAnswer(@Param("pid") Integer pId, @Param("torfId") Integer singleId, @Param("answer") Boolean answer);

    /**
     * 更新用户答案
     * @param pId 试卷id
     * @param singleId 题目id
     * @param answer 答案
     */
    void updateEssayUserAnswer(@Param("pid") Integer pId, @Param("essayId") Integer singleId, @Param("answer") String answer);

    /**
     * 修改状态
     * @param id
     * @param status
     */
    void updateStatus(@Param("pid") Integer id, @Param("status")Boolean status, @Param("time") LocalDateTime updateTime);

    /**
     * 更新成绩
     * @param id
     * @param grade
     */
    void updateGrade(@Param("pid") Integer id, @Param("grade")Float grade);

    /**
     * 更新解答题分数
     * @param pid
     * @param qid
     * @param grade
     */
    void updateEssayGrade(@Param("pid") Integer pid, @Param("qid") Integer qid, @Param("grade")Float grade);

    /**
     * 删除所有试卷
     * @param cid 课程id
     * @param sid 学生id
     */
    void deleteAllPaperForStu(@Param("cid") Integer cid, @Param("sid") Integer sid);
}
