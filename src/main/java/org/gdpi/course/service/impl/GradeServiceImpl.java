package org.gdpi.course.service.impl;

import org.gdpi.course.entity.GradeTable;
import org.gdpi.course.mapper.GradeMapper;
import org.gdpi.course.service.GradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public GradeTable findGradeTable(Integer cid, Integer sid) {
        return gradeMapper.findGradeTable(cid, sid);
    }

    @Override
    public Integer updateGradeTable(GradeTable gradeTable) {
        return gradeMapper.updateGradeTable(gradeTable);
    }

    @Override
    public List<GradeTable> findByCourseId(Integer id) {
        return gradeMapper.findByCourseId(id);
    }

    @Override
    public GradeTable findById(Integer id) {
        return gradeMapper.findById(id);
    }

    @Override
    public Integer deleteById(Integer id) {
        return gradeMapper.deleteById(id);
    }
}
