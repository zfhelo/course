package org.gdpi.course.service.impl;

import org.gdpi.course.entity.SingleQuestion;
import org.gdpi.course.mapper.SingleQuestionMapper;
import org.gdpi.course.service.SingleQuestionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
@Transactional
public class SingleQuestionServiceImpl implements SingleQuestionService {

    @Resource
    private SingleQuestionMapper singleQuestionMapper;

    @Override
    public Integer addSingleQue(SingleQuestion singleQuestion) {
        return singleQuestionMapper.addSingleQue(singleQuestion);
    }

    @Override
    public SingleQuestion findByIdAndCourseId(Integer id, Integer cid) {
       return singleQuestionMapper.findByIdAndCourseId(id, cid);
    }

    @Override
    public List<SingleQuestion> findByCourseId(Integer cid) {
        return singleQuestionMapper.findByCourseId(cid);
    }

    @Override
    @Cacheable(cacheNames = "single", key = "#root.args[0]", unless = "#result == null")
    public SingleQuestion findById(Integer id) {
        return singleQuestionMapper.findById(id);
    }

    @Override
    public Integer deleteById(Integer id) {
        return singleQuestionMapper.deleteById(id);
    }

    @Override
    public Integer updateSingleQuestion(SingleQuestion singleQuestion) {
        return singleQuestionMapper.updateSingleQuestion(singleQuestion);
    }

    @Override
    public Integer findReferenceNum(Integer id) {
        return singleQuestionMapper.findReferenceNum(id);
    }
}
