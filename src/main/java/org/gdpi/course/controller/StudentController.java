package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gdpi.course.entity.*;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhf
 */
@Controller
@RequestMapping("/stu")
public class StudentController {

    @Resource
    private StudentService studentService;
    @Resource
    private CourseService courseService;

    @Resource
    private HomeworkService homeworkService;

    @Resource
    private ExamPaperService examPaperService;
    @Resource
    private ResourceService resourceService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private StudentInvitationService studentInvitationService;
    @Resource
    private TeacherInvitationService teacherInvitationService;

    /**
     * 初始化课程页
     * @param userDetails
     * @return
     */
    @GetMapping("/course")
    public ModelAndView initCourse(@AuthenticationPrincipal UserDetails userDetails,
                             ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());
        List<Course> course = studentService.findCourseById(stu.getId());


        mv.addObject("course", course);
        mv.addObject("user", stu);
        mv.setViewName("stu/course");

        return mv;
    }

    /**
     * 搜索课程
     * @param key
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/searchCourse")
    @ResponseBody
    public SimpleResponse findCourse(@RequestParam String key,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        Student stu = studentService.findByUsername(userDetails.getUsername());


        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10)
                .doSelectPageInfo(() -> studentService.findCourse("%" + key + "%", stu.getId()));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 加入课程
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/joinCourse/{id:\\d+}")
    @ResponseBody
    public SimpleResponse joinCourse(@PathVariable Integer id,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        Course course = courseService.findById(id);
        if (course == null) {
            return SimpleResponse.error("课程不存在");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());

        Integer count = studentService.findBySidAndCourseId(stu.getId(), id);

        if (count != 0) {
            return SimpleResponse.error("你已经加入了该课程");
        }

        Integer num = studentService.insertJoinCourse(stu.getId(), id);

        if (num == 0) {
            return SimpleResponse.error("加入失败");
        }

        return SimpleResponse.success();

    }

    @DeleteMapping("/exit/course/{id:\\d+}")
    @ResponseBody
    public SimpleResponse exitCourse(@PathVariable Integer id,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        // 删除选课
        courseService.exitCourse(id, stu.getId());
        // 删除试卷
        examPaperService.deleteAllPaperForStu(id, stu.getId());
        // 删除作业
        homeworkService.deleteByAllForStu(id, stu.getId());
        // 删除帖子
        studentInvitationService.deleteByAllForStu(id, stu.getId());
        // 删除资源
        resourceService.deleteByAllForStu(id, stu.getId());
        return SimpleResponse.success();
    }

    /**
     * 初始化
     * @param userDetails
     * @param mv
     * @return
     */
    @GetMapping("/examIndex")
    public ModelAndView examIndex(@AuthenticationPrincipal UserDetails userDetails,
                                  ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());

        List<Course> courses = studentService.initExamIndex(stu.getId());
        mv.addObject("course", courses);
        mv.addObject("user", stu);
       mv.setViewName("stu/exam_index");
       return mv;
    }

    /**
     * 初始化
     * @param courseId
     * @param userDetails
     * @param mv
     * @return
     */
    @GetMapping("/examList")
    public ModelAndView examList(@RequestParam Integer courseId,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());

        List<ExamPaper> paper = examPaperService.findAllBySid(stu.getId(), courseId);
        ArrayList<ExamPaper> result = new ArrayList<>();
        // 去除没有发布的
        paper.forEach(examPaper -> {
            LocalDateTime startTime = examPaper.getExamModel().getStartTime();
            if (startTime.compareTo(LocalDateTime.now()) < 0) {
                result.add(examPaper);
            }
        });

        mv.addObject("course", courseService.findById(courseId));
        mv.addObject("user", stu);
        mv.addObject("paper", result);
        mv.setViewName("stu/exam_list");
        return mv;
    }

    /**
     * 初始化资源页
     * @param userDetails
     * @param mv
     * @return
     */
    @GetMapping("/resources")
    public ModelAndView initResources(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv,@RequestParam(defaultValue = "1") Integer page) {
        Student student = studentService.findByUsername(userDetails.getUsername());
        PageInfo<StudentResource> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findResourcesStu(student.getId()));
        pageInfo.getList().forEach(studentResource -> {
            if (studentResource.getStuId().equals(student.getId())) {
                studentResource.setIsSelf(true);
            }
        });
        mv.addObject("user", student);
        mv.addObject("page", pageInfo);
        mv.setViewName("stu/file-upload");
        return mv;
    }

    /**
     * 初始化作业页面
     * @param userDetails
     * @param mv
     * @return
     */
    @GetMapping("/homework")
    public ModelAndView initHomework(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {
        Student student = studentService.findByUsername(userDetails.getUsername());

        List<Course> courses = courseService.findHomework(student.getId());
        courses.forEach(course -> {
            List<Homework> submit = homeworkService.findSubmit(course.getId(), student.getId());
            List<Homework> overdue = homeworkService.findOverdue(course.getId(), student.getId());
            course.setSubmitHomework(submit);
            course.setOverdueHomework(overdue);
        });


        mv.addObject("user", student);
        mv.addObject("courses", courses);
        mv.setViewName("stu/homework_index");
        return mv;
    }

    /**
     * 获取指定课程作业
     * @param userDetails
     * @param mv
     * @param id
     * @return
     */
    @GetMapping("/homework/{id:\\d+}")
    public ModelAndView initHomeworkDetail(@AuthenticationPrincipal UserDetails userDetails,
                                           ModelAndView mv,
                                           @PathVariable Integer id) {
        Student stu = studentService.findByUsername(userDetails.getUsername());

        List<Homework> h = homeworkService.findByCourseIdForStu(id, stu.getId());

        h.forEach(homework -> {
            homework.setStudentHomework(homeworkService.findByHomeworkIdAndStuId(homework.getId(), stu.getId()));
        });
        mv.addObject("course", courseService.findById(id));
        mv.addObject("user", stu);
        mv.addObject("homework", h);
        mv.setViewName("stu/homework_detail");

        return mv;
    }

    @GetMapping("/homework/edit/{id:\\d+}")
    public ModelAndView editHomework(@AuthenticationPrincipal UserDetails userDetails,
                                     ModelAndView mv,
                                     @PathVariable Integer id) {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        Homework homework = homeworkService.findById(id);

        if (homework.getEndTime().compareTo(LocalDateTime.now()) < 0) {
            homework.setEndTime(null);
        }


        StudentHomework studentHomework = homeworkService.findByHomeworkIdAndStuId(homework.getId(), stu.getId());
        homework.setStudentHomework(studentHomework);

        mv.addObject("user", stu);
        mv.addObject("homework", homework);
        mv.setViewName("stu/homework");
        return mv;
    }

    @PutMapping("/homework")
    @ResponseBody
    public SimpleResponse submitHomework(@RequestBody StudentHomework studentHomework,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        if (studentHomework.getId() == null) {
            return SimpleResponse.error("未知作业");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());
        StudentHomework h = homeworkService.findStuHomeworkById(studentHomework.getId());
        if (h == null || !h.getStuId().equals(stu.getId())) {
            return SimpleResponse.error("没有权限");
        }
        Integer num = homeworkService.updateHomework(studentHomework);
        if (num == 0) {
            return SimpleResponse.error("更新失败");
        }
        return SimpleResponse.success();
    }

    @GetMapping("/profile")
    @ResponseBody
    public ModelAndView profile(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mv) {

        Student stu = studentService.findByUsername(userDetails.getUsername());
        mv.addObject("user", stu);
        mv.setViewName("stu/profile");

        return mv;
    }

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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        stu.setNickname(name);
        studentService.updateById(stu);
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        stu.setPhone(phone);
        studentService.updateById(stu);
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        stu.setPhoto(photo);
        studentService.updateById(stu);
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        stu.setEmail(emailCode.getTo());
        studentService.updateById(stu);
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

        Student stu = studentService.findByUsername(userDetails.getUsername());
        if (!passwordEncoder.matches(password, stu.getPassword())) {
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        if (!passwordEncoder.matches(oldPassword,stu.getPassword())) {
            return SimpleResponse.error("原密码错误");
        }
        stu.setPassword(passwordEncoder.encode(password));
        studentService.updateById(stu);
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

        Student stu = studentService.findByUsername(userDetails.getUsername());

        List<StudentInvitation> page = studentInvitationService.findBySid(stu.getId());
        List<Course> course = courseService.findCourseBySid(stu.getId());
        mv.addObject("user", stu);
        mv.addObject("page", page);
        mv.addObject("course", course);
        mv.setViewName("stu/invitation");
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

        Student stu = studentService.findByUsername(userDetails.getUsername());
        mv.addObject("user", stu);
        mv.setViewName("stu/add_invitation");
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        List<Comment> comments = studentInvitationService.findByInvitationId(id);
        mv.addObject("user", stu);
        mv.addObject("isStu", true);
        mv.addObject("article", invitation);
        mv.addObject("comments", comments);
        mv.setViewName("stu/invitation_detail");
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
        Student stu = studentService.findByUsername(userDetails.getUsername());
        List<Comment> comments = teacherInvitationService.findByInvitationId(id);
        mv.addObject("user", stu);
        mv.addObject("isStu", false);
        mv.addObject("article", invitation);
        mv.addObject("comments", comments);
        mv.setViewName("stu/invitation_detail");
        return mv;
    }
}
