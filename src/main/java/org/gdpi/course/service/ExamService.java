package org.gdpi.course.service;

import org.gdpi.course.entity.ExamModel;
import org.gdpi.course.entity.ExamModelDetail;
import org.gdpi.course.exception.QuestionLibraryNotEnough;

import java.util.List;

/**
 * @author zhf
 */
public interface ExamService {

    /**
     * 创建试卷模型
     * @param examModel
     */
    void addExamModel(ExamModelDetail examModel) throws QuestionLibraryNotEnough;

    /**
     * 查询所有模板试卷
     * @param cid
     * @return
     */
    List<ExamModel> findByCourseId(Integer cid);


    /**
     * 查找模板试卷
     * @param id
     * @return
     */
    ExamModel findById(Integer id);

    /**
     * 更新模板试卷
     * @param examModel
     * @return
     */
    Integer updateExamModel(ExamModel examModel);


    /**
     * 删除模板试卷
     * @param id
     * @return
     */
    Integer deleteById(Integer id);
}
