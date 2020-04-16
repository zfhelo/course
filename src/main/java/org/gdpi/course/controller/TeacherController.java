package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.EmailCode;
import org.gdpi.course.entity.StudentResource;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.ResourceService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@RestController
@RequestMapping("/tea")
public class TeacherController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private ResourceService resourceService;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 初始化管理页数据
     * @return
     */
    @GetMapping("/admin")
    public SimpleResponse enterAdmin(@AuthenticationPrincipal UserDetails userDetails) {
        Teacher teacher = teacherService.findByUsername(userDetails.getUsername());
        teacher.setPassword("");
        return SimpleResponse.success(teacher);
    }

    @GetMapping("/resources")
    public ModelAndView initResources(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        PageInfo<StudentResource> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> resourceService.findResourcesTea(tea.getId()));
        pageInfo.getList().forEach(studentResource -> studentResource.setIsSelf(true));

        mv.addObject("user", tea);
        mv.addObject("page", pageInfo);
        mv.setViewName("tea/file-upload");
        return mv;
    }

    @GetMapping("/profile")
    @ResponseBody
    public ModelAndView profile(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        mv.addObject("user", tea);
        mv.setViewName("tea/profile");

        return mv;
    }

    //----------

    /**
     * 修改姓名
     * @param name
     * @param userDetails
     * @return
     */
    @PutMapping("/edit/name")
    @ResponseBody
    public SimpleResponse editName(@RequestParam String name,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        tea.setNickname(name);
        teacherService.updateById(tea);
        return SimpleResponse.success();
    }
    /**
     * 修改手机
     * @param phone
     * @param userDetails
     * @return
     */
    @PutMapping("/edit/phone")
    @ResponseBody
    public SimpleResponse editPhone(@RequestParam String phone,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        tea.setPhone(phone);
        teacherService.updateById(tea);
        return SimpleResponse.success();
    }

    /**
     * 修改头像
     * @param photo
     * @param userDetails
     * @return
     */
    @PutMapping("/edit/photo")
    @ResponseBody
    public SimpleResponse editPhoto(@RequestParam String photo,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        tea.setPhoto(photo);
        teacherService.updateById(tea);
        return SimpleResponse.success();
    }
    /**
     * 修改邮箱
     * @param code 验证码
     * @param userDetails
     * @return
     */
    @PutMapping("/edit/email")
    @ResponseBody
    public SimpleResponse editEmail(@RequestParam String code,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    @SessionAttribute("EMAIL_CODE") EmailCode emailCode,
                                    SessionStatus sessionStatus) {
        if (!emailCode.getCode().equalsIgnoreCase(code)) {
            return SimpleResponse.error("验证码错误");
        }
        sessionStatus.setComplete();
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        tea.setEmail(emailCode.getTo());
        teacherService.updateById(tea);
        return SimpleResponse.success(emailCode.getTo());
    }

    /**
     * 校验密码
     * @param password
     * @param userDetails
     * @return
     */
    @PostMapping("/check/password")
    @ResponseBody
    public SimpleResponse checkPassword(@RequestParam String password,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        if (!passwordEncoder.matches(password, tea.getPassword())) {
            return SimpleResponse.error();
        }
        return SimpleResponse.success();
    }

    /**
     * 修改密码
     * @param code 验证码
     * @param userDetails
     * @return
     */
    @PutMapping("/edit/password")
    @ResponseBody
    public SimpleResponse editPassword(@RequestParam String code,@RequestParam String password,
                                       @RequestParam String oldPassword,
                                       @AuthenticationPrincipal UserDetails userDetails,
                                       @SessionAttribute("EMAIL_CODE") EmailCode emailCode,
                                       SessionStatus sessionStatus) {
        if (!emailCode.getCode().equalsIgnoreCase(code)) {
            return SimpleResponse.error("验证码错误");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        if (!passwordEncoder.matches(oldPassword,tea.getPassword())) {
            return SimpleResponse.error("原密码错误");
        }
        tea.setPassword(passwordEncoder.encode(password));
        teacherService.updateById(tea);
        sessionStatus.setComplete();
        return SimpleResponse.success();
    }



}
