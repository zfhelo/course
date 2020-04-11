package org.gdpi.course.service.impl;

import org.gdpi.course.entity.ExamPaper;
import org.gdpi.course.mapper.ExamPaperMapper;
import org.gdpi.course.service.ExamPaperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
}
