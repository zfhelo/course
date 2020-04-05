package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.exception.CourseAlreadyExistedException;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zhf
 */
@RestController
@RequestMapping("/tea")
public class CourseController {

    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    @PostMapping("/course")
    public SimpleResponse addCourse(@RequestBody @Valid Course course,
                                    BindingResult result,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return SimpleResponse.error("创建失败");
        }

        try {
            courseService.addCourse(course, userDetails.getUsername());
        } catch (CourseAlreadyExistedException e) {
            return SimpleResponse.error("课程号已存在");
        }
        return SimpleResponse.success("创建成功");
    }

    @GetMapping("/course")
    public SimpleResponse listCourse(@RequestParam(defaultValue = "1") Integer page,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        // 先查出教师id
        Teacher teacher = teacherService.findByUsername(userDetails.getUsername());

        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10)
                .doSelectPageInfo(() -> courseService.findByTeaId(teacher.getId()));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 分页查询
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/course/{id:\\d+}")
    public SimpleResponse getCourse(@PathVariable Integer id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        // 先查出教师id
        Teacher teacher = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(id, teacher.getId());

        if (course == null) {
            return SimpleResponse.error("查询失败");
        }

        return SimpleResponse.success(course);
    }

    @DeleteMapping("/course/{id:\\d+}")
    public SimpleResponse deleteCourse(@PathVariable Integer id,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        courseService.deleteByIdAndTeaId(id, tea.getId());

        return SimpleResponse.success("删除成功");
    }

    @PutMapping("/course")
    public SimpleResponse updateCourse(@RequestBody @Valid Course course,
                                       BindingResult result,
                                       @AuthenticationPrincipal UserDetails userDetails) {


        if (result.hasErrors()) {
            return SimpleResponse.error("修改不合法");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        course.setTeaId(tea.getId());

        Integer num = courseService.updateCourse(course);

        if (num == 0) {
            return SimpleResponse.error("修改失败");
        }

        return SimpleResponse.success(course);

    }
}
