package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 查询已提交的试卷
     * @param sid
     * @return
     */
    List<ExamPaper> findSubmitBySid(Integer sid);

    /**
     * 查询该学生在指定课程下的试卷
     * @param sid
     * @return
     */
    List<ExamPaper> findAllBySid(@Param("sid") Integer sid, @Param("cid") Integer cid);
}
