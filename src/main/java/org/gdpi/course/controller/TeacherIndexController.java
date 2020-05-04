package org.gdpi.course.controller;

import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Homework;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.HomeworkService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@RestController
@RequestMapping("/tea")
public class TeacherIndexController {
    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseService courseService;

    @Resource
    private HomeworkService homeworkService;

    @GetMapping("/homeworks/index/{cid:\\d+}")
    public SimpleResponse getAllHomework(@PathVariable Integer cid,
                                         @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(cid, tea.getId());
        if (course == null) {
            return SimpleResponse.error("未知课程");
        }

        List<Homework> homework = homeworkService.findByHomeworkByCid(course.getId());
        return SimpleResponse.success(homework);
    }
}
