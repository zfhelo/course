package org.gdpi.course.controller;

import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhf
 */
@RestController
@RequestMapping("/tea")
public class TeacherController {

    @Resource
    private TeacherService teacherService;
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
}
