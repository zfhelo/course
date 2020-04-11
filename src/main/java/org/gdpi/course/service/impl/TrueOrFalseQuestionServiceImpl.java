package org.gdpi.course.service.impl;

import org.gdpi.course.entity.TrueOrFalseQuestion;
import org.gdpi.course.mapper.TrueOrFalseQuestionMapper;
import org.gdpi.course.service.TrueOrFalseQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
@Transactional
public class TrueOrFalseQuestionServiceImpl implements TrueOrFalseQuestionService {

    @Resource
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;

    @Override
    public TrueOrFalseQuestion findById(Integer id) {
        return trueOrFalseQuestionMapper.findById(id);
    }

    @Override
    public TrueOrFalseQuestion findByIdAndCourseId(Integer id, Integer courseId) {
        return trueOrFalseQuestionMapper.findByIdAndCourseId(id, courseId);
    }

    @Override
    public List<TrueOrFalseQuestion> findByCourseId(Integer courseId) {
        return trueOrFalseQuestionMapper.findByCourseId(courseId);
    }

    @Override
    public Integer addTrueOrFalseQuestion(TrueOrFalseQuestion trueOrFalseQuestion) {
        return trueOrFalseQuestionMapper.addTrueOrFalseQuestion(trueOrFalseQuestion);
    }

    @Override
    public Integer updateById(TrueOrFalseQuestion trueOrFalseQuestion) {
        return trueOrFalseQuestionMapper.updateById(trueOrFalseQuestion);
    }

    @Override
    public Integer findReferenceNum(Integer id) {
        return trueOrFalseQuestionMapper.findReferenceNum(id);
    }

    @Override
    public Integer deleteById(Integer id) {


        return trueOrFalseQuestionMapper.deleteById(id);
    }
}
