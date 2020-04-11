package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.GapFillingQuestion;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.GapFillingQuestionService;
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
public class GapFillingQuestionController {


    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private GapFillingQuestionService gapFillingQuestionService;

    /**
     * 添加题目
     * @param gapFillingQuestion
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/gap")
    public SimpleResponse addQue(@RequestBody @Valid GapFillingQuestion gapFillingQuestion,
                                 BindingResult result,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        BeanUtils.trim(gapFillingQuestion);

        if (result.hasErrors() || gapFillingQuestion.getCourseId() == null) {
            return SimpleResponse.error("数据不合法");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course =
                courseService.findByIdAndTeaId(gapFillingQuestion.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("你没有该课程");
        }

        Integer num = gapFillingQuestionService.addGapFillingQuestion(gapFillingQuestion);

        if (num == 0) {
            return SimpleResponse.error("添加失败");
        }

        return SimpleResponse.success(gapFillingQuestion);
    }


    /**
     * 分页查询
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/gap")
    public SimpleResponse listQue(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam String number,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        Course course = courseService.findByNumber(number);
        if (course == null) {
            return SimpleResponse.error("未知课程");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        if (tea.getId() != course.getTeaId()) {
            return SimpleResponse.error("你没有该课程");
        }

        PageInfo<Object> pageInfo =
                PageHelper.startPage(page, 10).doSelectPageInfo(() -> gapFillingQuestionService.findByCourseId(course.getId()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("course", course);
        map.put("page", pageInfo);

        return SimpleResponse.success(map);
    }


    /**
     * 查询题目
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/gap/{id:\\d+}")
    public SimpleResponse getQue(@PathVariable Integer id,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        GapFillingQuestion que = gapFillingQuestionService.findById(id);
        if (que == null) {
            return SimpleResponse.error("没有该题目");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("你没有该题目");
        }

        return SimpleResponse.success(que);

    }

    /**
     * 删除题目
     * @param id
     * @param userDetails
     * @return
     */
    @DeleteMapping("/gap/{id:\\d+}")
    public SimpleResponse deleteQue(@PathVariable Integer id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        GapFillingQuestion que = gapFillingQuestionService.findById(id);

        if (que == null) {
            return SimpleResponse.error("没有该题目");
        }

        Integer referenceNum = gapFillingQuestionService.findReferenceNum(que.getId());
        if (referenceNum > 0) {
            return SimpleResponse.error("该题目被其他试题引用无法删除");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("你没用该题目");
        }

        Integer num = gapFillingQuestionService.deleteById(id);

        if (num == 0) {
            return SimpleResponse.error("删除失败");
        }

        return SimpleResponse.success(que);
    }

    /**
     * 修改题目
     * @param gapFillingQuestion
     * @param result
     * @param userDetails
     * @return
     */
    @PutMapping("/gap")
    public SimpleResponse updateQue(@RequestBody @Valid GapFillingQuestion gapFillingQuestion,
                                    BindingResult result,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(gapFillingQuestion);

        if (result.hasErrors() || gapFillingQuestion.getId() == null) {
            return SimpleResponse.error("数据不合法");
        }

        GapFillingQuestion que = gapFillingQuestionService.findById(gapFillingQuestion.getId());

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("你没有该题目");
        }

        Integer num = gapFillingQuestionService.updateById(gapFillingQuestion);

        if (num == 0) {
            SimpleResponse.error("删除失败");
        }

        return SimpleResponse.success(gapFillingQuestion);
    }
}
