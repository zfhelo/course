package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.SingleQuestion;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.SingleQuestionService;
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
public class SingleQuestionController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;
    @Resource
    private SingleQuestionService singleQuestionService;

    /**
     * 添加题目
     * @param singleQuestion
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/single")
    public SimpleResponse addSingleQuestion(@RequestBody @Valid SingleQuestion singleQuestion,
                                            BindingResult result, @AuthenticationPrincipal UserDetails userDetails) {

        BeanUtils.trim(singleQuestion);
        // 数据校验
        if (result.hasErrors() || singleQuestion.getCourseId() == null) {
            return SimpleResponse.error("数据不合法");
        }

        // 获取教师
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        // 查找上传者是否拥有该课程
        Course course =
                courseService.findByIdAndTeaId(singleQuestion.getCourseId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("您没有该课程");
        }

        Integer num = singleQuestionService.addSingleQue(singleQuestion);

        if (num == 0) {
            return SimpleResponse.error("添加失败");
        }

        return SimpleResponse.success(singleQuestion);
    }

    /**
     * 查找题目
     * @param id 题目id
     * @param userDetails
     * @return
     */
    @GetMapping("/single/{id:\\d+}")
    public SimpleResponse findSingleQuestion(@PathVariable Integer id,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        SingleQuestion que = singleQuestionService.findById(id);

        if (que == null) {
            return SimpleResponse.error("未找到该题目");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("你没有该题目");
        }

        return SimpleResponse.success(que);
    }

    /**
     * 分页查询
     * @param page
     * @param courseNumber 课程号
     * @param userDetails
     * @return
     */
    @GetMapping("/single")
    public SimpleResponse listSingleQue(@RequestParam( defaultValue = "1") Integer page,
                                        @RequestParam String courseNumber,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumber(courseNumber);

        if (course == null || (!tea.getId().equals(course.getTeaId()))) {
            return SimpleResponse.error("未查到该课程");
        }

        PageInfo<Object> pageInfo
                = PageHelper.startPage(page, 10)
                        .doSelectPageInfo(() -> singleQuestionService.findByCourseId(course.getId()));


        HashMap<String, Object> map = new HashMap<>();
        map.put("course", course);
        map.put("page", pageInfo);

        return SimpleResponse.success(map);
    }

    /**
     * 删除题目
     * @param id
     * @param userDetails
     * @return
     */
    @DeleteMapping("/single/{id:\\d+}")
    public SimpleResponse deleteSingleQuestion(@PathVariable Integer id,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        // 查出题目
        SingleQuestion question = singleQuestionService.findById(id);

        if (question == null) {
            return SimpleResponse.error("没有该题目");
        }

        Integer referenceNum = singleQuestionService.findReferenceNum(question.getId());
        if (referenceNum > 0) {
            return SimpleResponse.error("改题目被试卷使用无法删除");
        }

        // 查出教师
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        // 查出课程
        Course course = courseService.findByIdAndTeaId(question.getCourseId(), tea.getId());

        // 该教师没有改题目
        if (course == null) {
            return SimpleResponse.error("你没有该题目");
        }

        Integer num = singleQuestionService.deleteById(id);
        if (num == 0) {
            return SimpleResponse.error("删除失败");
        }

        return SimpleResponse.success(question);
    }

    /**
     * 修改题目
     * @param singleQuestion
     * @param result
     * @param userDetails
     * @return
     */
    @PutMapping("/single")
    public SimpleResponse updateSingleQuestion(@RequestBody @Valid SingleQuestion singleQuestion,
                                               BindingResult result,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(singleQuestion);
        if (result.hasErrors() || singleQuestion.getId() == null) {
            return SimpleResponse.error("数据不合法");
        }

        SingleQuestion question = singleQuestionService.findById(singleQuestion.getId());
        if (question == null) {
            return SimpleResponse.error("题目不存在");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(question.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("你没有该课程");
        }

        Integer num = singleQuestionService.updateSingleQuestion(singleQuestion);
        if (num == 0) {
            return SimpleResponse.error("修改失败");
        }
        return SimpleResponse.success(singleQuestion);
    }
}
