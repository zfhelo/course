package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.*;
import org.gdpi.course.exception.ExamPaperNotFoundException;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zhf
 */
@Controller
public class ExamPaperController {

    @Resource
    private ExamService examService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;
    @Resource
    private ExamPaperService examPaperService;
    @Resource
    private StudentService studentService;

    /**
     * 分页查找试卷
     * @param modelId
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/paper")
    @ResponseBody
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

    /**
     * 考试获取题目
     * @param id
     * @param userDetails
     * @param mv
     * @return
     */
    @GetMapping("/stu/exam/{id:\\d+}")
    public ModelAndView exam(@PathVariable Integer id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             ModelAndView mv) {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        ExamPaper paper = examPaperService.findById(id);

        if (paper == null || !stu.getId().equals(paper.getStuId())) {
            throw new ExamPaperNotFoundException("试卷没有找到");
        }
        ExamPaper questions;
        if (paper.getStatus()) {
            questions = examPaperService.getQuestionsForTea(id);
            mv.setViewName("stu/exam-preview");
        } else {
            questions = examPaperService.getQuestionsForStu(id);
            mv.setViewName("stu/exam");
        }

        mv.addObject("user", stu);
        mv.addObject("questions", questions);

        return mv;

    }
    /**
     * 考试获取题目
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/paper/{id:\\d+}")
    @ResponseBody
    public SimpleResponse exam(@PathVariable Integer id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        ExamPaper paper = examPaperService.findById(id);
        if (paper == null) {
            return SimpleResponse.error("未知试卷");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course =
                courseService.findByIdAndTeaId(paper.getExamModel().getCourseId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        ExamPaper questions = examPaperService.getQuestionsForTea(id);

        return SimpleResponse.success(questions);

    }


    /**
     * 提交试卷
     * @param que 答案
     * @param id 试卷id
     * @param userDetails
     * @return
     */
    @PutMapping("/stu/saveExam/{id:\\d+}")
    @ResponseBody
    public SimpleResponse saveExam(@RequestBody Map<String, Map<Integer, Object>> que,
                                     @PathVariable Integer id,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        ExamPaper paper = examPaperService.findById(id);

        if (paper == null) {
            return SimpleResponse.error("试卷不存在");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());
        if (paper.getStuId() != stu.getId()) {
            return SimpleResponse.error("权限不足");
        }
        if (paper.getStatus()) {
            return SimpleResponse.error("只可以提交一次");
        }
        if (paper.getExamModel().getIsHide()) {
            return SimpleResponse.error("无效操作");
        }
        if (paper.getExamModel().getEndTime().compareTo(LocalDateTime.now()) < 0) {
            return SimpleResponse.error("已结束");
        }
        examPaperService.saveUserAnswer(que, id);


        return SimpleResponse.success();
    }

    @PutMapping("/stu/submitExam/{id:\\d+}")
    @ResponseBody
    public SimpleResponse submitExam(@RequestBody Map<String, Map<Integer, Object>> que,
                                   @PathVariable Integer id,
                                   @AuthenticationPrincipal UserDetails userDetails) {


        ExamPaper paper = examPaperService.findById(id);

        if (paper == null) {
            return SimpleResponse.error("试卷不存在");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());
        if (paper.getStuId() != stu.getId()) {
            return SimpleResponse.error("权限不足");
        }
        if (paper.getStatus()) {
            return SimpleResponse.error("只可以提交一次");
        }
        if (paper.getExamModel().getIsHide()) {
            return SimpleResponse.error("无效操作");
        }
        if (paper.getExamModel().getEndTime().compareTo(LocalDateTime.now()) < 0) {
            return SimpleResponse.error("已结束");
        }

        examPaperService.submitUserAnswer(que, id);

        return SimpleResponse.success();
    }

    @PutMapping("/tea/essayGrade/{id:\\d+}")
    @ResponseBody
    public SimpleResponse submitEssayGrade(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody Map<Integer, Float> grade,
                                           @PathVariable Integer id) {

        ExamPaper paper = examPaperService.findById(id);
        if (paper == null) {
            return SimpleResponse.error("未知试卷");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(paper.getExamModel().getCourseId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        examPaperService.addEssayGrade(grade, id);
        return SimpleResponse.success();
    }
}
