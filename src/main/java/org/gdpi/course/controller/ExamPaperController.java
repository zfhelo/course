package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.ExamModel;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.ExamPaperService;
import org.gdpi.course.service.ExamService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@RestController
public class ExamPaperController {

    @Resource
    private ExamService examService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;
    @Resource
    private ExamPaperService examPaperService;

    /**
     * 分页查找试卷
     * @param modelId
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/paper")
    public SimpleResponse listPaper(@RequestParam Integer modelId,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        ExamModel model = examService.findById(modelId);
        if (model == null) {
            return SimpleResponse.error("未知试卷");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(model.getCourseId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("权限不足");
        }

        PageInfo<Object> pageInfo =
                PageHelper.startPage(page, 10)
                        .doSelectPageInfo(() -> examPaperService.findByModelId(modelId));

        return SimpleResponse.success(pageInfo);
    }
}
