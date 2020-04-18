package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.*;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.StudentInvitationService;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherInvitationService;
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
@RequestMapping("/stu")
public class StudentInvitationController {

    @Resource
    private StudentInvitationService studentInvitationService;
    @Resource
    private TeacherInvitationService teacherInvitationService;
    @Resource
    private StudentService studentService;
    @Resource
    private CourseService courseService;

    /**
     * 发布帖子
     * @param studentInvitation
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/invitation")
    public SimpleResponse addInvitation(@RequestBody @Valid StudentInvitation studentInvitation,
                                        BindingResult result,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        BeanUtils.trim(studentInvitation);
        if (result.hasErrors()) {
            return SimpleResponse.error("数据不合法");
        }
        if (studentInvitation.getCourseNumber() == null) {
            return SimpleResponse.error("请输入课程号");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumberAndSid(studentInvitation.getCourseNumber(), stu.getId());

        if (course == null) {
            return SimpleResponse.error("未知课程或为加入该课程");
        }

        studentInvitation.setStuId(stu.getId());
        studentInvitation.setCourseId(course.getId());

        Integer num = studentInvitationService.addInvitation(studentInvitation);
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByNumberAndSid(number, stu.getId());
        if (course == null) {
            return SimpleResponse.error("你未加入该课程");
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByNumberAndSid(number, stu.getId());
        if (course == null) {
            return SimpleResponse.error("你未加入该课程");
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
        Student stu = studentService.findByUsername(userDetails.getUsername());

        StudentInvitation invitation = studentInvitationService.findById(comment.getInvitationId());
        Course course = courseService.findById(invitation.getCourseId());
        if (courseService.findByNumberAndSid(course.getNumber(), stu.getId()) == null) {
            return SimpleResponse.error("你未加入该课程");
        }
        comment.setUserName(stu.getNickname());
        comment.setHeadImg(stu.getPhoto());

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
        Student stu = studentService.findByUsername(userDetails.getUsername());

        TeacherInvitation invitation = teacherInvitationService.findById(comment.getInvitationId());
        Course course = courseService.findById(invitation.getCourseId());

        if (courseService.findByNumberAndSid(course.getNumber(), stu.getId()) == null) {
            return SimpleResponse.error("你未加入该课程");
        }
        comment.setUserName(stu.getNickname());
        comment.setHeadImg(stu.getPhoto());

        teacherInvitationService.addComment(comment);

        return SimpleResponse.success();
    }

}
