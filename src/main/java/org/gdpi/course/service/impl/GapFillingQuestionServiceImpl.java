package org.gdpi.course.service.impl;

import org.gdpi.course.entity.GapFillingQuestion;
import org.gdpi.course.mapper.GapFillingQuestionMapper;
import org.gdpi.course.service.GapFillingQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
@Transactional
public class GapFillingQuestionServiceImpl implements GapFillingQuestionService {

    @Resource
    private GapFillingQuestionMapper gapFillingQuestionMapper;

    @Override
    public GapFillingQuestion findById(Integer id) {
        return gapFillingQuestionMapper.findById(id);
    }

    @Override
    public GapFillingQuestion findByIdAndCourseId(Integer id, Integer courseId) {
        return gapFillingQuestionMapper.findByIdAndCourseId(id, courseId);
    }

    @Override
    public List<GapFillingQuestion> findByCourseId(Integer courseId) {
        return gapFillingQuestionMapper.findByCourseId(courseId);
    }

    @Override
    public Integer addGapFillingQuestion(GapFillingQuestion gapFillingQuestion) {
        return gapFillingQuestionMapper.addGapFillingQuestion(gapFillingQuestion);
    }

    @Override
    public Integer updateById(GapFillingQuestion gapFillingQuestion) {
        return gapFillingQuestionMapper.updateById(gapFillingQuestion);
    }

    @Override
    public Integer deleteById(Integer id) {

        return gapFillingQuestionMapper.deleteById(id);
    }

    @Override
    public Integer findReferenceNum(Integer id) {
        return gapFillingQuestionMapper.findReferenceNum(id);
    }
}
