package org.gdpi.course.controller;

import org.gdpi.course.entity.EmailCode;
import org.gdpi.course.entity.Student;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.entity.User;
import org.gdpi.course.exception.UserAlreadyExistedException;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherService;
import org.gdpi.course.util.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

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
    @Resource
    private PasswordEncoder passwordEncoder;

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
     * 修改密码
     * @param request
     * @param isStu
     * @param username
     * @param password
     * @param emailCode
     * @return
     */
    @PostMapping("/public/findPassword")
    @ResponseBody
    public SimpleResponse updatePassword(HttpServletRequest request,
                                         @RequestParam Boolean isStu,
                                         @RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String emailCode) {
        Object obj = request.getSession().getAttribute("EMAIL_CODE");
        if (obj == null) {
            return SimpleResponse.error("请先获取验证码");
        }
        EmailCode code = (EmailCode) obj;
        if (code.getTimeout().compareTo(LocalDateTime.now()) < 0) {
            return SimpleResponse.error("验证码已过期");
        }
        if (!code.getCode().equals(emailCode)) {
            return SimpleResponse.error("验证码错误");
        }
        User user;
        if (isStu) {
            user = studentService.findByUsername(username);
        } else {
            user = teacherService.findByUsername(username);
        }

        if (user == null) {
            return SimpleResponse.error("不存在该账号");
        }

        if (!user.getEmail().equals(code.getTo())) {
            return SimpleResponse.error("请勿尝试修改他人密码");
        }
        user.setPassword(passwordEncoder.encode(password));
        if (isStu) {
            studentService.updateById((Student) user);
        } else {
            teacherService.updateById((Teacher) user);
        }
        request.getSession().invalidate();
        return SimpleResponse.success("修改成功");
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
    public ModelAndView indexTea(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        mv.addObject("user", tea);
        mv.setViewName("tea/index");
        return mv;
    }

    /**
     * 初始化首页数据
     * @param userDetails
     * @return
     */
    @GetMapping("/stu/index")
    public ModelAndView indexStu(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        mv.addObject("user", stu);
        mv.setViewName("stu/index");
        return mv;
    }

    /**
     * 根据用户登录信息智能跳转
     * @param userDetails
     * @return
     */
    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {
        // 教师
        for (GrantedAuthority permissions:userDetails.getAuthorities()) {
            if ("ROLE_TEA".equals(permissions.getAuthority())) {
                return indexTea(userDetails, mv);
            }
        }
        // 学生
        return indexStu(userDetails, mv);
    }


}
