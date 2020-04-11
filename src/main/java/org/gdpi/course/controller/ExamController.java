package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.ExamModel;
import org.gdpi.course.entity.ExamModelDetail;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.exception.QuestionLibraryNotEnough;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.ExamService;
import org.gdpi.course.service.TeacherService;
import org.gdpi.course.util.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author zhf
 */
@RestController
@RequestMapping("/tea")
public class ExamController {

    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private ExamService examService;

    /**
     * 创建模板试卷
     * @param examModelDetail
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/exam")
    public SimpleResponse addExam(@RequestBody @Valid ExamModelDetail examModelDetail,
                                  BindingResult result,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(examModelDetail);

        if (result.hasErrors()) {
            return SimpleResponse.error("数据不合法");
        }

        Course course = courseService.findByNumber(examModelDetail.getCourseNum());
        if (course == null) {
            return SimpleResponse.error("未知课程");
        }

        Teacher tea= teacherService.findByUsername(userDetails.getUsername());
        if (course.getTeaId() != tea.getId()) {
            return SimpleResponse.error("权限不足");
        }

        examModelDetail.setCourseId(course.getId());
        examModelDetail.setGrade(examModelDetail.getSingleGrade()
                + examModelDetail.getEssayGrade()
                + examModelDetail.getGapGrade()
                + examModelDetail.getTorfGrade()
        );

        try {
            examService.addExamModel(examModelDetail);
        } catch (QuestionLibraryNotEnough questionLibraryNotEnough) {
            return SimpleResponse.error(questionLibraryNotEnough.getMessage());
        }

        return SimpleResponse.success();
    }


    /**
     * 分页查找
     * @param page
     * @param courseNumber
     * @param userDetails
     * @return
     */
    @GetMapping("/exam")
    public SimpleResponse listExamModel(@RequestParam(defaultValue = "1") Integer page, @RequestParam String courseNumber,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        Course course = courseService.findByNumber(courseNumber);
        if (course == null) {
            return SimpleResponse.error("未知课程");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        if (course.getTeaId() != tea.getId()) {
            return SimpleResponse.error("权限不足");
        }

        PageInfo<Object> pageInfo =
                PageHelper.startPage(page, 10)
                        .doSelectPageInfo(() -> examService.findByCourseId(course.getId()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("course", course);
        map.put("page", pageInfo);

        return SimpleResponse.success(map);
    }

    /**
     * 获取题目
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/exam/{id:\\d+}")
    public SimpleResponse findExamModel(@PathVariable Integer id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        ExamModel model = examService.findById(id);
        if (model == null) {
            return SimpleResponse.error("未知模板试卷");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(model.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("权限不足");
        }

        return SimpleResponse.success(model);


    }


    @PutMapping("/exam")
    public SimpleResponse updateExamModel(@RequestBody @Valid ExamModel examModel,
                                          BindingResult result,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(examModel);
        if (result.hasErrors() || examModel.getId() == null) {
            return SimpleResponse.error("数据不合法");
        }
        ExamModel model = examService.findById(examModel.getId());
        if (model == null) {
            return SimpleResponse.error("试卷不存在");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(model.getCourseId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        Integer num = examService.updateExamModel(examModel);
        if (num == 0) {
            return SimpleResponse.error("修改失败");
        }
        return SimpleResponse.success(examModel);
    }

    @DeleteMapping("/exam/{id:\\d+}")
    public SimpleResponse deleteExamModel(@PathVariable Integer id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        ExamModel model = examService.findById(id);
        if (model == null) {
            return SimpleResponse.error("未知测试");
        }

        Teacher teacher = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(model.getCourseId(), teacher.getId());

        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        Integer num = examService.deleteById(id);

        if (num == 0) {
            return SimpleResponse.error("删除失败");
        }

        return SimpleResponse.success(model);

    }


}
