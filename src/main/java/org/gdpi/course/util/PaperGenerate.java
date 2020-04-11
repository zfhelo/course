package org.gdpi.course.util;

import org.gdpi.course.entity.ExamModelDetail;
import org.gdpi.course.entity.Question;
import org.gdpi.course.exception.QuestionLibraryNotEnough;

import java.util.*;

/**
 * @author zhf
 */
public class PaperGenerate {

    /**
     * 检查题目是否可以生成至少一套试卷
     * @param model
     * @param questions
     * @return
     */
    public static Map<String, List<Set<Integer>>> generate(ExamModelDetail model, Map<String, List<? extends Question>> questions) throws QuestionLibraryNotEnough {

        Map<String, List<Set<Integer>>> repository = new HashMap<>();

        // 拿到选择题
        List<? extends Question> singles = questions.get("singles");
        if (singles != null) {
            // 根据要求生成试卷
            List<Set<Integer>> papers = calSum(singles, model.getSingleGrade(), model.getSingleNum());
            if (papers.size() == 0) {
                throw new QuestionLibraryNotEnough("题库中的单选题无法满足要求");
            }
            repository.put("singles",papers);

        }
        // 拿到选择题
        List<? extends Question> torfs = questions.get("torfs");
        if (torfs != null) {
            // 根据要求生成试卷
            List<Set<Integer>> papers = calSum(torfs, model.getTorfGrade(), model.getTorfNum());
            if (papers.size() == 0) {
                throw new QuestionLibraryNotEnough("题库中的判断题无法满足要求");
            }
            repository.put("torfs",papers);

        }

        // 拿到填空题
        List<? extends Question> gaps = questions.get("gaps");
        if (gaps != null) {
            // 根据要求生成试卷
            List<Set<Integer>> papers = calSum(gaps, model.getGapGrade(), model.getGapNum());
            if (papers.size() == 0) {
                throw new QuestionLibraryNotEnough("题库中的填空题无法满足要求");
            }
            repository.put("gaps",papers);
        }

        // 拿到解答题
        List<? extends Question> essays = questions.get("essays");
        if (essays != null) {
            // 根据要求生成试卷
            List<Set<Integer>> papers = calSum(essays, model.getEssayGrade(), model.getEssayNum());
            if (papers.size() == 0) {
                throw new QuestionLibraryNotEnough("题库中的问答题无法满足要求");
            }
            repository.put("essays",papers);
        }

        return repository;
    }

    /**
     * 生成套题
     * @param questions 所有题目
     * @param result 总分数
     * @param count 题目个数
     */
    private static List<Set<Integer>> calSum(List<? extends Question> questions, int result, int count) {
        // 保存生成的每套试题
        List<Set<Integer>> papers = new ArrayList<>();
        for (int i = 1; i < 1 << questions.size(); i++)//从1循环到2^N
        {
            int sum=0;
            // 保存生成的一套试题 值为 题目的id
            Set<Integer> paper = new HashSet<>();
            for (int j = 0; j < questions.size(); j++)
            {
                if ((i & 1 << j) != 0)//用i与2^j进行位与运算，若结果不为0,则表示第j位不为0,从数组中取出第j个数
                {
                    sum += questions.get(j).getGrade();
                    paper.add(questions.get(j).getId());
                }
            }
            if (sum == result&&count==paper.size()) {
                papers.add(paper);
            }
        }
        return papers;
    }
}
