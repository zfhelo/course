package org.gdpi.course.service;

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
}
