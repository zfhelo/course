package org.gdpi.course.service;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.ExamPaper;

import java.util.List;
import java.util.Map;

/**
 * @author zhf
 */
public interface ExamPaperService {

    /**
     * 通过模板试卷查找所有试卷
     * @param modelId
     * @return
     */
    List<ExamPaper> findByModelId(Integer modelId);

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
     * 通过试卷id获得题目并获取用户答案
     * @param id
     * @return
     */
    ExamPaper getQuestionsForStu(Integer id);

    /**
     * 通过试卷id获得题目并获取用户答案
     * @param id
     * @return
     */
    ExamPaper getQuestionsForTea(Integer id);

    /**
     * 提交答案
     * @param answer
     * @param pid
     */
    void submitUserAnswer(Map<String, Map<Integer, Object>> answer, Integer pid);

    /**
     * 保存答案
     * @param answer
     * @param pid
     */
    void saveUserAnswer(Map<String, Map<Integer, Object>> answer, Integer pid);

    /**
     * 添加解答题成绩
     * @param grade
     * @param pid
     */
    void addEssayGrade(Map<Integer, Float> grade, Integer pid);

    void deleteAllPaperForStu(Integer cid, Integer sid);
}
