package org.gdpi.course.controller;

import org.gdpi.course.entity.EmailCode;
import org.gdpi.course.entity.Student;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.entity.User;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherService;
import org.gdpi.course.service.ValidationCodeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

/**
 * @author zhf
 */
@RestController
@SessionAttributes("EMAIL_CODE")
public class ValidationController {

    @Resource
    private ValidationCodeService validationCodeService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;

    /**
     * 修改邮箱
     * @param emailCode
     * @param modelMap
     * @param email
     * @return
     * @throws MessagingException
     */
    @GetMapping("/public/emailCode")
    public SimpleResponse code(@SessionAttribute(required = false, value = "EMAIL_CODE") EmailCode emailCode,
                               ModelMap modelMap,@Email @RequestParam String email) throws MessagingException {

        if (emailCode == null || emailCode.getTimeout().compareTo(LocalDateTime.now()) < 0) {
            EmailCode code = validationCodeService.sendEmailCode(email);
            modelMap.addAttribute("EMAIL_CODE", code);
            return SimpleResponse.success("请查看邮箱");
        }
        if (!emailCode.getTo().equals(email)) {
            EmailCode code = validationCodeService.sendEmailCode(email);
            modelMap.addAttribute("EMAIL_CODE", code);
        }
        return SimpleResponse.success("请查看邮箱");

    }

    @GetMapping("/stu/emailCode")
    public SimpleResponse codeStu(@SessionAttribute(required = false, value = "EMAIL_CODE") EmailCode emailCode,
                               ModelMap modelMap,
                               @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        String email = stu.getEmail();
        if (email == null || email.equals("")) {
            return SimpleResponse.error("请先绑定邮箱");
        }
        return code(emailCode, modelMap, email);
    }

    @GetMapping("/tea/emailCode")
    public SimpleResponse codeTea(@SessionAttribute(required = false, value = "EMAIL_CODE") EmailCode emailCode,
                               ModelMap modelMap,
                               @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        String email = tea.getEmail();
        if (email == null || email.equals("")) {
            return SimpleResponse.error("请先绑定邮箱");
        }
        return code(emailCode, modelMap, email);
    }


    /**
     * 找回密码发送邮件
     * @param isStu
     * @param username
     * @return
     * @throws MessagingException
     */
    @GetMapping("/public/findPassword")
    public SimpleResponse findPassword(HttpServletRequest request,
                                       @RequestParam Boolean isStu,
                                       @RequestParam String username) throws MessagingException {
        User user = null;
        if (isStu) {
            user = studentService.findByUsername(username);
        } else {
            user = teacherService.findByUsername(username);
        }
        if (user == null) {
            return SimpleResponse.error("请检查用户名是否正确");
        }
        if (user.getEmail() == null) {
            return SimpleResponse.error("该账号未绑定邮箱!");
        }
        Object obj = request.getSession().getAttribute("EMAIL_CODE");

        if (obj == null) {
            EmailCode code = validationCodeService.sendEmailCode(user.getEmail());
            request.getSession().setAttribute("EMAIL_CODE", code);
            return SimpleResponse.success("请查看邮箱");
        }

        EmailCode emailCode = (EmailCode) obj;
        if (!emailCode.getTo().equals(user.getEmail())) {
            EmailCode code = validationCodeService.sendEmailCode(user.getEmail());
            request.getSession().setAttribute("EMAIL_CODE", code);
            return SimpleResponse.success("请查看邮箱");
        }

        if (emailCode.getTimeout().compareTo(LocalDateTime.now()) < 0) {
            EmailCode code = validationCodeService.sendEmailCode(user.getEmail());
            request.getSession().setAttribute("EMAIL_CODE", code);
        }
        return SimpleResponse.success("请查看邮箱");
    }
}
