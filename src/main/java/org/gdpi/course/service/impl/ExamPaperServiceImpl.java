package org.gdpi.course.service.impl;

import org.gdpi.course.entity.ExamPaper;
import org.gdpi.course.mapper.ExamPaperMapper;
import org.gdpi.course.service.ExamPaperService;
import org.gdpi.course.util.QuestionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhf
 */
@Service
@Transactional
public class ExamPaperServiceImpl implements ExamPaperService {

    @Resource
    private ExamPaperMapper examPaperMapper;

    @Override
    public List<ExamPaper> findByModelId(Integer modelId) {
        return examPaperMapper.findByModelId(modelId);
    }

    @Override
    public List<ExamPaper> findAllBySid(Integer sid, Integer cid) {
        return examPaperMapper.findAllBySid(sid, cid);
    }

    @Override
    public ExamPaper findById(Integer id) {
        return examPaperMapper.findById(id);
    }

    @Override
    public ExamPaper getQuestionsForStu(Integer id) {
        ExamPaper question = examPaperMapper.getQuestion(id);
        QuestionUtils.upsetSingle(question.getSingleQues(), question.getRule());
        return question;
    }
    @Override
    public ExamPaper getQuestionsForTea(Integer id) {
        ExamPaper question = examPaperMapper.getQuestion(id);
        return question;
    }


    private void updateUserAnswer(Map<String, Map<Integer, Object>> answer, Integer pid) {

        Map<Integer, Object> single = answer.get("single");
        if (single != null && single.size() != 0) {
            single.forEach((key, value) -> {
                examPaperMapper.updateSingleUserAnswer(pid, key, (String) value);
            });
        }

        Map<Integer, Object> gap = answer.get("gap");
        if (gap != null && gap.size() != 0) {
            gap.forEach((key, value) -> {
                examPaperMapper.updateGapUserAnswer(pid, key, (String) value);
            });
        }

        Map<Integer, Object> torf = answer.get("torf");
        if (torf != null && torf.size() != 0) {
            torf.forEach((key, value) -> {
                String v = (String) value;
                Boolean flag = false;
                if ("true".equals(v)) {
                    flag = true;
                }
                examPaperMapper.updateTorfUserAnswer(pid, key, flag);
            });
        }

        Map<Integer, Object> essay = answer.get("essay");
        if (essay != null && essay.size() != 0) {
            essay.forEach((key, value) -> {
                examPaperMapper.updateEssayUserAnswer(pid, key, (String) value);
            });
        }


    }

    @Override
    public void submitUserAnswer(Map<String, Map<Integer, Object>> answer, Integer pid) {
        updateUserAnswer(answer, pid);
        // 更改状态为已提交
        examPaperMapper.updateStatus(pid, true, LocalDateTime.now());
        // 计算成绩..
        Float sum = calcGrade(pid);
        examPaperMapper.updateGrade(pid, (float) sum);
    }

    private Float calcGrade(Integer pid) {
        // 计算成绩..
        final ArrayList<Float> grade = new ArrayList<>();
        // 计算选择题成绩
        ExamPaper question = examPaperMapper.getQuestion(pid);
        question.getSingleQues().forEach(singleQuestion -> {
            if (singleQuestion.getChoose1().equals(singleQuestion.getUserAnswer())) {
                grade.add(singleQuestion.getGrade());
            }
        });
        // 计算判断题
        question.getTorfQues().forEach(trueOrFalseQuestion -> {
            if (trueOrFalseQuestion.getAnswer().equals(trueOrFalseQuestion.getUserAnswer())) {
                grade.add(trueOrFalseQuestion.getGrade());
            }
        });
        // 填空题答案
        question.getGapQues().forEach(gapFillingQuestion -> {
            if (gapFillingQuestion.getAnswer().equals(gapFillingQuestion.getUserAnswer())) {
                grade.add(gapFillingQuestion.getGrade());
            }
        });
        question.getEssayQues().forEach(essayQuestion -> {
            grade.add(essayQuestion.getUserGrade());
        });


        Float sum = (float)grade.stream().mapToDouble(value -> value).sum();
        return sum;
    }

    @Override
    public void saveUserAnswer(Map<String, Map<Integer, Object>> answer, Integer pid) {
        updateUserAnswer(answer, pid);
    }

    @Override
    public void addEssayGrade(Map<Integer, Float> gradeEssay, Integer pid) {
        gradeEssay.forEach((qid, grade) -> {
            if (grade != null) {
                examPaperMapper.updateEssayGrade(pid, qid, grade);
            }
            // 计算成绩..
            Float sum = calcGrade(pid);
            examPaperMapper.updateGrade(pid, (float) sum);
        });
    }

    @Override
    public void deleteAllPaperForStu(Integer cid, Integer sid) {
        examPaperMapper.deleteAllPaperForStu(cid, sid);
    }
}
