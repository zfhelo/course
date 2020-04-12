package org.gdpi.course.service;

import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.ExamPaper;

import java.util.List;

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
}
