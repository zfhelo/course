package org.gdpi.course.controller;

import org.gdpi.course.entity.Student;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.exception.UserAlreadyExistedException;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherService;
import org.gdpi.course.util.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 账号创建
 * 密码找回
 * 初始化首页数据
 *
 * @author zhf
 */
@Controller
public class UserController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;

    /**
     * 创建教师用户
     *
     * @param teacher
     * @param result
     * @return
     */
    @PostMapping("/public/tea")
    @ResponseBody
    public SimpleResponse createTea(@Valid Teacher teacher, BindingResult result,
                                    @Value("${default.tea.head-url}") String headImage) {
        BeanUtils.trim(teacher);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                System.out.println(objectError.getDefaultMessage());
            });
            return SimpleResponse.error("注册失败");
        }
        teacher.setNickname(teacher.getUsername());
        teacher.setPhoto(headImage);

        try {
            teacherService.addTeacher(teacher);
        } catch (UserAlreadyExistedException e) {
            return SimpleResponse.error(e.getMessage());
        }

        return SimpleResponse.success();
    }

    /**
     * 创建学生用户
     *
     * @param student
     * @param result
     * @return
     */
    @PostMapping("/public/stu")
    @ResponseBody
    public SimpleResponse createStu(@Valid Student student, BindingResult result,
                                    @Value("${default.stu.head-url}") String headImage) {
        BeanUtils.trim(student);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                System.out.println(objectError.getDefaultMessage());
            });
            return SimpleResponse.error("注册失败");
        }
        student.setNickname(student.getUsername());
        student.setPhoto(headImage);

        try {
            studentService.addStudent(student);
        } catch (UserAlreadyExistedException e) {
            return SimpleResponse.error(e.getMessage());
        }

        return SimpleResponse.success();
    }

    /**
     * 验证用户是否存在
     *
     * @param username
     * @return
     */
    @GetMapping("/public/tea/{username}")
    @ResponseBody
    public SimpleResponse findTeaByUsername(@PathVariable("username") String username) {

        if (teacherService.findByUsername(username) == null) {
            return SimpleResponse.success();
        } else {
            return SimpleResponse.error("用户已存在");
        }
    }

    /**
     * 验证用户是否存在
     *
     * @param username
     * @return
     */
    @GetMapping("/public/stu/{username}")
    @ResponseBody
    public SimpleResponse findStuByUsername(@PathVariable("username") String username) {

        if (studentService.findByUsername(username) == null) {
            return SimpleResponse.success();
        } else {
            return SimpleResponse.error("用户已存在");
        }
    }

    /**
     * 初始化首页数据
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/index")
    public String indexTea(@AuthenticationPrincipal UserDetails userDetails) {
        userDetails.getUsername();
        return "tea/index";
    }


    @GetMapping("/stu/index")
    public String indexStu(@AuthenticationPrincipal UserDetails userDetails) {
        userDetails.getUsername();
        return "stu/index";
    }

    /**
     * 根据用户登录信息智能跳转
     * @param userDetails
     * @return
     */
    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails) {
        // 教师
        for (GrantedAuthority permissions:userDetails.getAuthorities()) {
            if ("ROLE_TEA".equals(permissions.getAuthority())) {
                return indexTea(userDetails);
            }
        }
        // 学生
        return indexStu(userDetails);
    }

}
