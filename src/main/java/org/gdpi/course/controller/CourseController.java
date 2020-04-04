package org.gdpi.course.controller;

import org.gdpi.course.entity.Course;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zhf
 */
@RestController("/tea")
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping("/course")
    public SimpleResponse addCourse(@Valid Course course,
                                    BindingResult result,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        if (result.hasErrors()) {
            return SimpleResponse.error("创建失败");
        }

        courseService.addCourse(course, userDetails.getUsername());

        return SimpleResponse.success("创建成功");
    }
}
