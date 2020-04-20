package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gdpi.course.entity.ExamModel;
import org.gdpi.course.entity.ExamPaper;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface ExamMapper {
    /**
     * 添加模板试卷
     * @param examModel
     * @return
     */
    Integer addExamModel(ExamModel examModel);

    /**
     * 创建试卷
     * @return
     */
    Integer addExamPaper(@Param("stuId") Integer stuId,
                         @Param("modelId") Integer modelId,
                         @Param("rule") Integer rule);

    /**
     * 查找所有试卷
     * @param id
     * @return
     */
    List<ExamPaper> findByModelId(Integer id);

    /**
     * 添加选择题
     * @param paperId
     * @param singleId
     * @return
     */
    Integer addSingleQue(@Param("pid") Integer paperId, @Param("qid") Integer singleId);

    /**
     * 添加填空题
     * @param paperId
     * @param gapId
     * @return
     */
    Integer addGapQue(@Param("pid") Integer paperId, @Param("qid") Integer gapId);


    /**
     * 添加判断题
     * @param paperId
     * @param torfId
     * @return
     */
    Integer addTorfQue(@Param("pid") Integer paperId, @Param("qid") Integer torfId);


    /**
     * 添加问答题
     * @param paperId
     * @param essayId
     * @return
     */
    Integer addEssayQue(@Param("pid") Integer paperId, @Param("qid") Integer essayId);

    /**
     * 查询所有模板试卷
     * @param cid
     * @return
     */
    List<ExamModel> findByCourseIdPublish(Integer cid);

    /**
     * 查询所有模板试卷
     * @param cid
     * @return
     */
    List<ExamModel> findByCourseId(Integer cid);

    /**
     * 查找模板试卷提交人数
     * @param modelId
     * @return
     */
    Integer findSubmitNum(Integer modelId);

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

    /**
     * 查找已过期的试卷
     * @param cid
     * @return
     */
    List<ExamModel> findOverdue(@Param("cid") Integer cid);



}
