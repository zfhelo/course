package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.EssayQuestion;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.EssayQuestionService;
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
public class EssayQuestionController {

    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private EssayQuestionService essayQuestionService;

    /**
     * 添加题目
     * @param essayQuestion
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/essay")
    public SimpleResponse addEssayQuestion(@RequestBody @Valid EssayQuestion essayQuestion,
                                           BindingResult result,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(essayQuestion);
        if (result.hasErrors() || essayQuestion.getCourseId() == null) {
            return SimpleResponse.error("数据不合法");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(essayQuestion.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("课程不存在");
        }

        Integer num = essayQuestionService.addEssayQuestion(essayQuestion);
        if (num == 0) {
            return SimpleResponse.error("添加失败");
        }

        return SimpleResponse.success(essayQuestion);
    }

    /**
     * 分页查找
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/essay")
    public SimpleResponse listEssayQuestion(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam String number,
                                            @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumber(number);

        if (course == null || (!tea.getId().equals(course.getTeaId()))) {
            return SimpleResponse.error("你没有该课程");
        }

        PageInfo<Object> pageInfo =
                PageHelper.startPage(page, 10).doSelectPageInfo(() -> essayQuestionService.findByCourseId(course.getId()));

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
    @GetMapping("/essay/{id:\\d+}")
    public SimpleResponse getEssayQuestion(@PathVariable Integer id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        EssayQuestion que = essayQuestionService.findById(id);

        if (que == null) {
            return SimpleResponse.error("题目不存在");
        }


        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());
        if (course == null) {
            SimpleResponse.error("题目不存在");
        }

        return SimpleResponse.success(que);
    }

    /**
     * 修改题目
     * @param essayQuestion
     * @param userDetails
     * @return
     */
    @PutMapping("/essay")
    public SimpleResponse updateEssayQuestion(@RequestBody @Valid EssayQuestion essayQuestion,
                                              BindingResult result,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(essayQuestion);
        if (result.hasErrors() || essayQuestion.getId() == null) {
            return SimpleResponse.error("数据不合法");
        }

        EssayQuestion que = essayQuestionService.findById(essayQuestion.getId());
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("没有该题目");
        }

        Integer num = essayQuestionService.updateById(essayQuestion);
        if (num == 0) {
            return SimpleResponse.error("修改失败");
        }

        return SimpleResponse.success(essayQuestion);
    }

    /**
     * 删除题目
     * @param id
     * @param userDetails
     * @return
     */
    @DeleteMapping("/essay/{id:\\d+}")
    public SimpleResponse deleteEssayQuestion(@PathVariable Integer id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        EssayQuestion que = essayQuestionService.findById(id);

        if (que == null) {
            return SimpleResponse.error("未知题目");
        }

        Integer referenceNum = essayQuestionService.findReferenceNum(que.getId());
        if (referenceNum > 0) {
            return SimpleResponse.error("该题目被其他试题引用无法删除");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(que.getCourseId(), tea.getId());

        if (course == null) {
            SimpleResponse.error("你没有改题目");
        }

        Integer num = essayQuestionService.deleteById(id);
        if (num == 0) {
            return SimpleResponse.error("删除失败");
        }

        return SimpleResponse.success(que);
    }
}
