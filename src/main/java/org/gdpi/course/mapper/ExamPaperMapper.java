package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gdpi.course.entity.ExamPaper;

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
}
