package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.*;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private CourseService courseService;

    @Resource
    private StudentInvitationService studentInvitationService;
    @Resource
    private TeacherInvitationService teacherInvitationService;

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
                                    @SessionAttribute(value = "EMAIL_CODE", required = false) EmailCode emailCode,
                                    SessionStatus sessionStatus) {
        if (emailCode == null) {
            return SimpleResponse.error("请先获取验证码");
        }
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
                                       @SessionAttribute(value = "EMAIL_CODE", required = false) EmailCode emailCode,
                                       SessionStatus sessionStatus) {
        if (emailCode == null) {
            return SimpleResponse.error("请先获取验证码");
        }

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


    /**
     * 初始化讨论区
     * @param mv
     * @param userDetails
     * @return
     */
    @GetMapping("/invitation")
    public ModelAndView initInvitation(ModelAndView mv, @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        List<TeacherInvitation> page = teacherInvitationService.findByTid(tea.getId());
        List<Course> course = courseService.findByTeaId(tea.getId());
        mv.addObject("user", tea);
        mv.addObject("page", page);
        mv.addObject("course", course);
        mv.setViewName("tea/invitation");
        return mv;
    }

    /**
     * 跳转发送帖子页面
     * @param mv
     * @param userDetails
     * @return
     */
    @GetMapping("/addInvitation")
    public ModelAndView addInvitation(ModelAndView mv, @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        mv.addObject("user", tea);
        mv.setViewName("tea/add_invitation");
        return mv;
    }


    /**
     * 跳转帖子详情页面
     * @param mv
     * @param userDetails
     * @param id
     * @return
     */
    @GetMapping("/invitationStu/{id:\\d+}")
    public ModelAndView invitationDetailStu(ModelAndView mv, @AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Integer id) {

        StudentInvitation invitation = studentInvitationService.findById(id);
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        List<Comment> comments = studentInvitationService.findByInvitationId(id);
        mv.addObject("user", tea);
        mv.addObject("isStu", true);
        mv.addObject("article", invitation);
        mv.addObject("comments", comments);
        mv.setViewName("tea/invitation_detail");
        return mv;
    }
    /**
     * 跳转帖子详情页面
     * @param mv
     * @param userDetails
     * @param id
     * @return
     */
    @GetMapping("/invitationTea/{id:\\d+}")
    public ModelAndView invitationDetailTea(ModelAndView mv, @AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Integer id) {

        TeacherInvitation invitation = teacherInvitationService.findById(id);
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        List<Comment> comments = teacherInvitationService.findByInvitationId(id);
        mv.addObject("user", tea);
        mv.addObject("isStu", false);
        mv.addObject("article", invitation);
        mv.addObject("comments", comments);
        mv.setViewName("tea/invitation_detail");
        return mv;
    }
}


