package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.GradeTable;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.GradeService;
import org.gdpi.course.service.TeacherService;
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
public class GradeController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private GradeService gradeService;

    @Resource
    private CourseService courseService;

    /**
     * 查看成绩表
     * @param page
     * @param courseNumber
     * @param userDetails
     * @return
     */
    @GetMapping("/grade")
    public SimpleResponse listMember(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam String courseNumber,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        Course course = courseService.findByNumber(courseNumber);

        if (course == null) {
            return SimpleResponse.error("你没有该课程");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        if (!tea.getId().equals(course.getTeaId())) {
            return SimpleResponse.error("你没有该课程");
        }

        PageInfo<Object> pageInfo =
                PageHelper.startPage(page, 10)
                        .doSelectPageInfo(() -> gradeService.findByCourseId(course.getId()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("course", course);
        map.put("page", pageInfo);

        return SimpleResponse.success(map);
    }

    /**
     * 分页查询成绩表
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/grade/{id:\\d+}")
    public SimpleResponse getGrade(@PathVariable Integer id,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        GradeTable gradeTable = gradeService.findById(id);

        if (gradeTable == null) {
            return SimpleResponse.error("未查找到数据");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(gradeTable.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("未查找到数据");
        }

        return SimpleResponse.success(gradeTable);
    }

    /**
     * 修改成绩
     * @param gradeTable
     * @param userDetails
     * @return
     */
    @PutMapping("/grade")
    public SimpleResponse updateGrade(@RequestBody @Valid GradeTable gradeTable,
                                      BindingResult result,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println(gradeTable);
        if (result.hasErrors()) {
            return SimpleResponse.error("数据不合法");
        }

        GradeTable table = gradeService.findById(gradeTable.getId());

        if (table == null) {
            return SimpleResponse.error("成绩表不存在");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(table.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        Integer num = gradeService.updateGradeTable(gradeTable);

        if (num == 0) {
            return SimpleResponse.error("更新失败");
        }

        return SimpleResponse.success(gradeTable);
    }

    /**
     * 删除学员
     * @param id
     * @param userDetails
     * @return
     */
    @DeleteMapping("/grade/{id:\\d+}")
    public SimpleResponse deleteStudent(@PathVariable Integer id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        GradeTable grade = gradeService.findById(id);
        if (grade == null) {
            return SimpleResponse.error("没有该学员");
        }

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByIdAndTeaId(grade.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("未知课程");
        }

        Integer num = gradeService.deleteById(id);
        if (num == 0) {
            return SimpleResponse.error("删除失败");
        }
        //移除该学员其他资源

        return SimpleResponse.success();
    }
}
