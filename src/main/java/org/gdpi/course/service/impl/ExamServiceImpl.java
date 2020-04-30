package org.gdpi.course.service.impl;

import org.gdpi.course.entity.*;
import org.gdpi.course.exception.QuestionLibraryNotEnough;
import org.gdpi.course.mapper.*;
import org.gdpi.course.service.ExamService;
import org.gdpi.course.util.PaperGenerate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhf
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ExamServiceImpl implements ExamService {

    @Resource
    private EssayQuestionMapper essayQuestionMapper;
    @Resource
    private GapFillingQuestionMapper gapFillingQuestionMapper;
    @Resource
    private SingleQuestionMapper singleQuestionMapper;
    @Resource
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private ExamMapper examMapper;

    @Override
    public void addExamModel(ExamModelDetail examModel) throws QuestionLibraryNotEnough {
        // 1. 查找题库是否能够符合要求
        Map<String, List<? extends Question>> questions = getQuestions(examModel);
        // 2. 生成试题
        Map<String, List<Set<Integer>>> papers = PaperGenerate.generate(examModel, questions);

        // 3. 添加模板试卷
        examMapper.addExamModel(examModel);
        // 4. 获取课程所有学员
        List<Integer> stuId = courseMapper.findStudentId(examModel.getCourseId());

        // 5. 未每个学员创建一张试卷
        Random random = new Random();
        for (Integer sId:stuId) {
            examMapper.addExamPaper(sId, examModel.getId(), random.nextInt());
        }
        // 5. 获取所有的试卷id
        List<ExamPaper> paper = examMapper.findByModelId(examModel.getId());
        // 6. 随机分发
        addQuestionMap(papers, paper);
    }

    @Override
    public List<ExamModel> findByCourseId(Integer cid) {
        return examMapper.findByCourseId(cid);
    }

    @Override
    public ExamModel findById(Integer id) {
        return examMapper.findById(id);
    }

    @Override
    public Integer updateExamModel(ExamModel examModel) {
        return examMapper.updateExamModel(examModel);
    }


    @Override
    public Integer deleteById(Integer id) {
        return examMapper.deleteById(id);
    }

    /**
     * 筛选题库中的题目
     * @param examModel
     * @return
     * @throws QuestionLibraryNotEnough
     */
    private Map<String, List<? extends Question>> getQuestions(ExamModelDetail examModel) throws QuestionLibraryNotEnough {
        HashMap<String, List<? extends Question>> map = new HashMap<>();

        // 有选择题
        if (examModel.getSingleNum() != null && examModel.getSingleNum() > 0) {
            List<SingleQuestion> singles =
                    singleQuestionMapper.findGradeLessThan(examModel.getSingleGrade(), examModel.getCourseId());
            if (singles.size() == 0) {
                throw new QuestionLibraryNotEnough("选择题无法满足要求");
            }

            map.put("singles", singles);
        }
        // 有填空题
        if (examModel.getGapNum() != null && examModel.getGapNum() > 0) {
            List<GapFillingQuestion> gaps =
                    gapFillingQuestionMapper.findGradeLessThan(examModel.getGapGrade(), examModel.getCourseId());
            if (gaps.size() == 0) {
                throw new QuestionLibraryNotEnough("填空题无法满足要求");
            }
            map.put("gaps", gaps);
        }
        // 判断题
        if (examModel.getTorfNum() != null && examModel.getTorfNum() > 0) {
            List<TrueOrFalseQuestion> torfs =
                    trueOrFalseQuestionMapper.findGradeLessThan(examModel.getTorfGrade(), examModel.getCourseId());
            if (torfs.size() == 0) {
                throw new QuestionLibraryNotEnough("判断题无法满足要求");
            }
            map.put("torfs", torfs);
        }
        // 问答题
        if (examModel.getEssayNum() != null && examModel.getEssayGrade() > 0) {
            List<EssayQuestion> essays =
                    essayQuestionMapper.findGradeLessThan(examModel.getEssayGrade(), examModel.getCourseId());
            if (essays.size() == 0) {
                throw new QuestionLibraryNotEnough("问答题无法满足要求");
            }
            map.put("essays", essays);
        }


        return map;
    }

    /**
     * 给试卷添加题目
     * @param papers 所有试卷题
     * @param examPapers 试卷
     */
    private void addQuestionMap(Map<String, List<Set<Integer>>> papers, List<ExamPaper> examPapers) {
        List<Set<Integer>> singles = papers.get("singles");
        List<Set<Integer>> gaps = papers.get("gaps");
        List<Set<Integer>> essays = papers.get("essays");
        List<Set<Integer>> torfs = papers.get("torfs");
        Random random = new Random();
        // 遍历试卷
        for (ExamPaper e:examPapers) {

            if (singles != null) {
                int i = random.nextInt(singles.size());
                // 随机拿取一套试题
                Set<Integer> question = singles.get(i);
                for (Integer qid:question) {
                    examMapper.addSingleQue(e.getId(), qid);
                }
            }

            if (gaps != null) {
                int i = random.nextInt(gaps.size());
                // 随机拿取一套试题
                Set<Integer> question = gaps.get(i);
                for (Integer qid:question) {
                    examMapper.addGapQue(e.getId(), qid);
                }
            }

            if (essays != null) {
                int i = random.nextInt(essays.size());
                // 随机拿取一套试题
                Set<Integer> question = essays.get(i);
                for (Integer qid:question) {
                    examMapper.addEssayQue(e.getId(), qid);
                }
            }

            if (torfs != null) {
                int i = random.nextInt(torfs.size());
                // 随机拿取一套试题
                Set<Integer> question = torfs.get(i);
                for (Integer qid:question) {
                    examMapper.addTorfQue(e.getId(), qid);
                }
            }
        }
    }
}
