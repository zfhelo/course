package org.gdpi.course.service.impl;

import org.gdpi.course.entity.EssayQuestion;
import org.gdpi.course.mapper.EssayQuestionMapper;
import org.gdpi.course.service.EssayQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
@Transactional
public class EssayQuestionServiceImpl implements EssayQuestionService {

    @Resource
    private EssayQuestionMapper essayQuestionMapper;

    @Override
    public EssayQuestion findById(Integer id) {
        return essayQuestionMapper.findById(id);
    }

    @Override
    public EssayQuestion findByIdAndCourseId(Integer id, Integer courseId) {
        return essayQuestionMapper.findByIdAndCourseId(id, courseId);
    }

    @Override
    public List<EssayQuestion> findByCourseId(Integer courseId) {
        return essayQuestionMapper.findByCourseId(courseId);
    }

    @Override
    public Integer addEssayQuestion(EssayQuestion essayQuestion) {
        return essayQuestionMapper.addEssayQuestion(essayQuestion);
    }

    @Override
    public Integer updateById(EssayQuestion essayQuestion) {
        return essayQuestionMapper.updateById(essayQuestion);
    }

    @Override
    public Integer deleteById(Integer id) {
        return essayQuestionMapper.deleteById(id);
    }

    @Override
    public Integer findReferenceNum(Integer id) {
        return essayQuestionMapper.findReferenceNum(id);
    }
}
