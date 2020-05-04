package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Homework;
import org.gdpi.course.entity.StudentHomework;
import org.gdpi.course.mapper.CourseMapper;
import org.gdpi.course.mapper.HomeworkMapper;
import org.gdpi.course.service.HomeworkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Resource
    private HomeworkMapper homeworkMapper;
    @Resource
    private CourseMapper courseMapper;

    @Override
    public Integer addHomeWork(Homework homework) {
        Integer num = homeworkMapper.addHomeWork(homework);
        List<Integer> students = courseMapper.findStudentId(homework.getCourseId());

        students.forEach(sid -> {
            homeworkMapper.addHomeworkForStu(homework.getId(), sid);
        });

        return num;
    }

    @Override
    public List<Homework> findByCourseId(Integer cid) {
        return homeworkMapper.findByCourseId(cid);
    }

    @Override
    public Integer deleteById(Integer id) {
        return homeworkMapper.deleteById(id);
    }

    @Override
    public Homework findById(Integer id) {
        return homeworkMapper.findById(id);
    }

    @Override
    public List<StudentHomework> findByHomeworkId(Integer id) {
        return homeworkMapper.findByHomeworkId(id);
    }

    @Override
    public StudentHomework findStuHomeworkById(Integer id) {
        return homeworkMapper.findStuHomeworkById(id);
    }

    @Override
    public Integer updateGradeById(StudentHomework studentHomework) {
        return homeworkMapper.updateGradeById(studentHomework);
    }

    @Override
    public List<Homework> findByCid(Integer cid) {
        return homeworkMapper.findByCid(cid);
    }

    @Override
    public List<Homework> findSubmit(Integer cid, Integer sid) {
        return homeworkMapper.findSubmit(cid, sid);
    }

    @Override
    public List<Homework> findOverdue(Integer cid, Integer sid) {
        return homeworkMapper.findOverdue(cid,sid);
    }

    @Override
    public List<Homework> findByCourseIdForStu(Integer cid, Integer sid) {
        return homeworkMapper.findByCourseIdForStu(cid, sid);
    }

    @Override
    public StudentHomework findByHomeworkIdAndStuId(Integer hid, Integer sid) {
        return homeworkMapper.findByHomeworkIdAndStuId(hid, sid);
    }

    @Override
    public Integer updateHomework(StudentHomework studentHomework) {
        return homeworkMapper.updateHomework(studentHomework);
    }

    @Override
    public void deleteByAllForStu(Integer cid, Integer sid) {
        homeworkMapper.deleteByAllForStu(cid, sid);
    }

    @Override
    public void findAllHomework(List<Course> courses, Integer sid) {
        courses.forEach(course -> {
            List<StudentHomework> allHomework = homeworkMapper.findAllHomework(course.getId(), sid);
            course.setStudentHomeworks(allHomework);
        });
    }


    @Override
    public List<Homework> findByHomeworkByCid(Integer cid) {
        return homeworkMapper.findByHomeworkByCid(cid);
    }
}

