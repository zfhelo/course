package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.*;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.*;
import org.gdpi.course.util.BeanUtils;
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
public class TeacherInvitationController {

    @Resource
    private StudentInvitationService studentInvitationService;
    @Resource
    private TeacherInvitationService teacherInvitationService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;

    /**
     * 发布帖子
     * @param teacherInvitation
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/invitation")
    public SimpleResponse addInvitation(@RequestBody @Valid TeacherInvitation teacherInvitation ,
                                        BindingResult result,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(teacherInvitation);
        if (result.hasErrors()) {
            return SimpleResponse.error("数据不合法");
        }
        if (teacherInvitation.getCourseNumber() == null) {
            return SimpleResponse.error("请输入课程号");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumber(teacherInvitation.getCourseNumber());
        if (course == null) {
            return SimpleResponse.error("没有该课程");
        }

        course = courseService.findByIdAndTeaId(course.getId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("你没有该课程");
        }

        teacherInvitation.setTeaId(tea.getId());
        teacherInvitation.setCourseId(course.getId());

        Integer num = teacherInvitationService.addInvitation(teacherInvitation);
        if (num == 0) {
            return SimpleResponse.error("发布失败");
        }

        return SimpleResponse.success();
    }


    /**
     * 分页查询学生帖子
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/listInvitationStu")
    public SimpleResponse listInvitationStu(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam String number, @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course1 = courseService.findByNumber(number);
        if (course1 == null) {
            return SimpleResponse.error("未知课程");
        }

        Course course = courseService.findByIdAndTeaId(course1.getId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("你没有该课程");
        }


        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> studentInvitationService.findByCourseId(course.getId()));

        return SimpleResponse.success(pageInfo);

    }
    /**
     * 分页查询教师帖子
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/listInvitationTea")
    public SimpleResponse listInvitationTea(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam String number, @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course1 = courseService.findByNumber(number);
        if (course1 == null) {
            return SimpleResponse.error("未知课程");
        }

        Course course = courseService.findByIdAndTeaId(course1.getId(), tea.getId());
        if (course == null) {
            return SimpleResponse.error("你没有该课程");
        }

        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> teacherInvitationService.findByCourseId(course.getId()));

        return SimpleResponse.success(pageInfo);

    }

    /**
     * 评论学生帖子
     * @param userDetails
     * @param comment
     * @return
     */
    @PostMapping("/commentStu")
    public SimpleResponse addCommentStu(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody Comment comment) {

        if (comment.getInvitationId() == null) {
            return SimpleResponse.error("未知帖子");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        StudentInvitation invitation = studentInvitationService.findById(comment.getInvitationId());

        if (courseService.findByIdAndTeaId(invitation.getCourseId(), tea.getId()) == null) {
            return SimpleResponse.error("没有权限");
        }
        comment.setUserName(tea.getNickname());
        comment.setHeadImg(tea.getPhoto());

        studentInvitationService.addComment(comment);

        return SimpleResponse.success();
    }
    /**
     * 评论教师帖子
     * @param userDetails
     * @param comment
     * @return
     */
    @PostMapping("/commentTea")
    public SimpleResponse addCommentTea(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody Comment comment) {

        if (comment.getInvitationId() == null) {
            return SimpleResponse.error("未知帖子");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        TeacherInvitation invitation = teacherInvitationService.findById(comment.getInvitationId());

        if (courseService.findByIdAndTeaId(invitation.getCourseId(), tea.getId()) == null) {
            return SimpleResponse.error("没有权限");
        }
        comment.setUserName(tea.getNickname());
        comment.setHeadImg(tea.getPhoto());

        teacherInvitationService.addComment(comment);

        return SimpleResponse.success();
    }

}
