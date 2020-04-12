package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.ExamPaper;
import org.gdpi.course.entity.Student;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.ExamPaperService;
import org.gdpi.course.service.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Controller
@RequestMapping("/stu")
public class StudentController {

    @Resource
    private StudentService studentService;
    @Resource
    private CourseService courseService;

    @Resource
    private ExamPaperService examPaperService;

    /**
     * 初始化课程页
     * @param userDetails
     * @return
     */
    @GetMapping("/course")
    public ModelAndView initCourse(@AuthenticationPrincipal UserDetails userDetails,
                             ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());
        List<Course> course = studentService.findCourseById(stu.getId());

        mv.addObject("course", course);

        mv.setViewName("stu/course");

        return mv;
    }

    /**
     * 搜索课程
     * @param key
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/searchCourse")
    @ResponseBody
    public SimpleResponse findCourse(@RequestParam String key,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        Student stu = studentService.findByUsername(userDetails.getUsername());


        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10)
                .doSelectPageInfo(() -> studentService.findCourse("%" + key + "%", stu.getId()));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 加入课程
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/joinCourse/{id:\\d+}")
    @ResponseBody
    public SimpleResponse joinCourse(@PathVariable Integer id,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        Course course = courseService.findById(id);
        if (course == null) {
            return SimpleResponse.error("课程不存在");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());

        Integer count = studentService.findBySidAndCourseId(stu.getId(), id);

        if (count != 0) {
            return SimpleResponse.error("你已经加入了该课程");
        }

        Integer num = studentService.insertJoinCourse(stu.getId(), id);

        if (num == 0) {
            return SimpleResponse.error("加入失败");
        }

        return SimpleResponse.success();

    }

    @GetMapping("/examIndex")
    public ModelAndView examIndex(@AuthenticationPrincipal UserDetails userDetails,
                                  ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());

        List<Course> courses = studentService.initExamIndex(stu.getId());
        mv.addObject("course", courses);
       mv.setViewName("stu/exam_index");
       return mv;
    }

    @GetMapping("/examList")
    public ModelAndView examList(@RequestParam Integer courseId,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());

        List<ExamPaper> paper = examPaperService.findAllBySid(stu.getId(), courseId);

        mv.addObject("paper", paper);
        mv.setViewName("stu/exam_list");
        return mv;
    }

}
