package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Homework;
import org.gdpi.course.entity.StudentHomework;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.HomeworkService;
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
public class HomeworkController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;
    @Resource
    private HomeworkService homeworkService;


    /**
     * 发布作业
     * @param homework
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/homework")
    public SimpleResponse addHomework(@RequestBody @Valid Homework homework,
                                      BindingResult result,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(homework);
        if (result.hasErrors() || homework.getCourseNumber() == null) {
            return SimpleResponse.error("数据不合法");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByNumber(homework.getCourseNumber());

        if (course == null || course.getTeaId() != tea.getId()) {
            return SimpleResponse.error("课程不存在");
        }

        homework.setCourseId(course.getId());
        Integer num = homeworkService.addHomeWork(homework);

        if (num == 0) {
            return SimpleResponse.error("添加失败");
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
    @GetMapping("/homework")
    public SimpleResponse listHomework(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam String courseNumber,
                                       @AuthenticationPrincipal UserDetails userDetails){

        Course course = courseService.findByNumber(courseNumber);

        if (course == null) {
            return SimpleResponse.error("未知课程");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        if (course.getTeaId() != tea.getId()) {
            return SimpleResponse.error("权限不足");
        }

        PageInfo<Object> p = PageHelper.startPage(page, 10)
                .doSelectPageInfo(() -> homeworkService.findByCourseId(course.getId()));


        HashMap<String, Object> map = new HashMap<>();
        map.put("course", course);
        map.put("page", p);
        return SimpleResponse.success(map);
    }

    /**
     * 删除课程
     * @param id
     * @param userDetails
     * @return
     */
    @DeleteMapping("/homework/{id:\\d+}")
    public SimpleResponse deleteHomework(@PathVariable Integer id,
                                         @AuthenticationPrincipal UserDetails userDetails) {

        Homework homework = homeworkService.findById(id);
        if (homework == null) {
            return SimpleResponse.error("未知的作业");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(homework.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        Integer num = homeworkService.deleteById(id);
        if (num == 0) {
            return SimpleResponse.error("删除失败");
        }
        return SimpleResponse.success(homework);
    }

    /**
     * 分页查找学生作业
     * @param page
     * @param homeworkId
     * @param userDetails
     * @return
     */
    @GetMapping("/homework/list")
    public SimpleResponse listHomeworkList(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam Integer homeworkId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Homework homework = homeworkService.findById(homeworkId);
        if (homework == null) {
            return SimpleResponse.error("作业不存在");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(homework.getCourseId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("权限不足");
        }

        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> homeworkService.findByHomeworkId(homeworkId));

        return SimpleResponse.success(pageInfo);

    }

    /**
     * 查看学生作业
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/homework/check/{id:\\d+}")
    public SimpleResponse checkHomework(@PathVariable Integer id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        StudentHomework homework = homeworkService.findStuHomeworkById(id);
        if (homework == null) {
            return SimpleResponse.error("未知作业");
        }
        Integer courseId = homework.getHomework().getCourseId();

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        if (courseService.findByIdAndTeaId(courseId, tea.getId()) == null) {
            return SimpleResponse.error("没有权限");
        }

        return SimpleResponse.success(homework);
    }

    /**
     * 修改成绩
     * @param studentHomework
     * @param userDetails
     * @return
     */
    @PutMapping("/homework/grade")
    public SimpleResponse updateGrade(@RequestBody StudentHomework studentHomework,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        if (studentHomework.getId() == null || studentHomework.getGrade() < 0) {
            return SimpleResponse.error("非法数据");
        }

        StudentHomework homework =
                homeworkService.findStuHomeworkById(studentHomework.getId());

        if (homework == null) {
            return SimpleResponse.error("不存在该作业");
        }

        Integer courseId = homework.getHomework().getCourseId();
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        if (courseService.findByIdAndTeaId(courseId, tea.getId()) == null) {
            return SimpleResponse.error("权限不足");
        }

        Integer num = homeworkService.updateGradeById(studentHomework);

        if (num == 0) {
            return SimpleResponse.error("更新失败");
        }

        return SimpleResponse.success();

    }

}
