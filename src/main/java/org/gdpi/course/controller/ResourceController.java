package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.gdpi.course.entity.*;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.ResourceService;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author zhf
 */
@RestController
public class ResourceController {

    @Resource
    private ResourceService resourceService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;


    /**
     * 学生上传资源
     * @param file
     * @param resource
     * @param userDetails
     * @return
     * @throws IOException
     */
    @PostMapping("/stu/upload")
    public SimpleResponse upload(@RequestParam("file") MultipartFile file,
                                 StudentResource resource,
                                 @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (resource.getCourseNumber() == null) {
            return SimpleResponse.error("未知课程");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumberAndSid(resource.getCourseNumber(), stu.getId());
        if (course == null) {
            return SimpleResponse.error("未知课程或为加入该课程");
        }

        resource.setStuId(stu.getId());
        resource.setCourseId(course.getId());
        resource.setSize(file.getSize());
        resourceService.saveFile(resource, file);

        return SimpleResponse.success();
    }

    /**
     * 教师上传资源
     * @param file
     * @param resource
     * @param userDetails
     * @return
     * @throws IOException
     */
    @PostMapping("/tea/upload")
    public SimpleResponse upload(@RequestParam("file") MultipartFile file,
                                 TeacherResource resource,
                                 @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (resource.getCourseNumber() == null) {
            return SimpleResponse.error("未知课程");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        Course course = courseService.findByNumber(resource.getCourseNumber());
        if (course == null) {
            return SimpleResponse.error("未知课程");
        }
        Course byIdAndTeaId = courseService.findByIdAndTeaId(course.getId(), tea.getId());
        if (byIdAndTeaId == null) {
            return SimpleResponse.error("你没有该课程");
        }
        resource.setTeaId(tea.getId());
        resource.setCourseId(course.getId());
        resource.setSize(file.getSize());
        resourceService.saveFile(resource, file);

        return SimpleResponse.success();
    }


    /**
     * 分页查找资源
     * 加入的所有课程的资源
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/stu/resources/list")
    public SimpleResponse listPage(@RequestParam Integer page, @AuthenticationPrincipal UserDetails userDetails) {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        PageInfo<StudentResource> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findResourcesStu(stu.getId()));
        pageInfo.getList().forEach(studentResource -> {
            if (studentResource.getStuId().equals(stu.getId())) {
                studentResource.setIsSelf(true);
            }
        });

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 教师分页查找资源
     * 所有学员的资源
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/resources/list")
    public SimpleResponse listPageTea(@RequestParam Integer page, @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());
        PageInfo<StudentResource> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findResourcesTea(tea.getId()));
        pageInfo.getList().forEach(studentResource -> studentResource.setIsSelf(true));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 学生筛选学生资源
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/stu/resources/stu")
    public SimpleResponse stuListPageForSearchStu(@RequestParam Integer page,
                                            @RequestParam String number,
                                            @AuthenticationPrincipal UserDetails userDetails) {

        Student stu = studentService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumberAndSid(number, stu.getId());

        if (course == null) {
            return SimpleResponse.error("课程不存在或未加入该课程");
        }

        PageInfo<StudentResource> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findByCourseIdStu(course.getId()));
        pageInfo.getList().forEach(studentResource -> {
            if (studentResource.getStuId().equals(stu.getId())) {
                studentResource.setIsSelf(true);
            }
        });

        return SimpleResponse.success(pageInfo);
    }
    /**
     * 教师筛选学生资源
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/resources/stu")
    public SimpleResponse teaListPageForSearchStu(@RequestParam Integer page,
                                            @RequestParam String number,
                                            @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumber(number);
        if (course == null) {
            return SimpleResponse.error("没有该课程");
        }
        Course course1 = courseService.findByIdAndTeaId(course.getId(), tea.getId());
        if (course1 == null) {
            return SimpleResponse.error("你没有该课程");
        }

        PageInfo<StudentResource> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findByCourseIdStu(course.getId()));
        pageInfo.getList().forEach(studentResource ->studentResource.setIsSelf(true));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 学生筛选教师资源
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/stu/resources/tea")
    public SimpleResponse stuListPageForSearchTea(@RequestParam Integer page,
                                            @RequestParam String number,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        Student stu = studentService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumberAndSid(number, stu.getId());

        if (course == null) {
            return SimpleResponse.error("课程不存在或未加入该课程");
        }

        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findByCourseIdTea(course.getId()));

        return SimpleResponse.success(pageInfo);
    }
    /**
     * 教师筛选教师资源
     * @param page
     * @param number
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/resources/tea")
    public SimpleResponse TeaListPageForSearchTea(@RequestParam Integer page,
                                            @RequestParam String number,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByNumber(number);
        if (course == null) {
            return SimpleResponse.error("课程不存在");
        }

        Course course1 = courseService.findByIdAndTeaId(course.getId(), tea.getId());
        if (course1 == null) {
            return SimpleResponse.error("你没有该课程");
        }

        PageInfo<TeacherResource> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> resourceService.findByCourseIdTea(course.getId()));
        pageInfo.getList().forEach(resource -> resource.setIsSelf(true));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 删除资源
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/stu/resource/{id:\\d+}")
    public SimpleResponse stuDeleteResource(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        StudentResource resource = resourceService.findByIdStu(id);
        if (resource == null) {
            return SimpleResponse.error("不存在该资源");
        }
        Student stu = studentService.findByUsername(userDetails.getUsername());

        if (!stu.getId().equals(resource.getStuId())) {
            return SimpleResponse.error("权限不足");
        }

        resourceService.deleteByIdStu(id);
        new File(resource.getPath()).delete();
        return SimpleResponse.success();
    }
    /**
     * 教师删除学生资源
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/tea/resource/stu/{id:\\d+}")
    public SimpleResponse teaDeleteResourceStu(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        StudentResource resource = resourceService.findByIdStu(id);
        if (resource == null) {
            return SimpleResponse.error("不存在该资源");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(resource.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        resourceService.deleteByIdStu(id);
        new File(resource.getPath()).delete();

        return SimpleResponse.success();
    }
    /**
     * 教师删除资源
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/tea/resource/tea/{id:\\d+}")
    public SimpleResponse teaDeleteResourceTea(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        TeacherResource resource = resourceService.findByIdTea(id);
        if (resource == null) {
            return SimpleResponse.error("不存在该资源");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        Course course = courseService.findByIdAndTeaId(resource.getCourseId(), tea.getId());

        if (course == null) {
            return SimpleResponse.error("没有权限");
        }

        resourceService.deleteByIdTea(id);
        new File(resource.getPath()).delete();

        return SimpleResponse.success();
    }

    @GetMapping("/public/res/tea/{id:\\d+}")
    public String downloadTea(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        TeacherResource res = resourceService.findByIdTea(id);
        if (res == null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("资源不存在");
            return null;
        }
        File file = new File(res.getPath());
        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("资源不存在");
            return null;
        }

        response.setContentType("application/octet-stream");
        response.addHeader("content-type", "octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName().split("_")[1], "UTF-8"));

        try (FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, response.getOutputStream());
        }


        return null;
    }

    @GetMapping("/public/res/stu/{id:\\d+}")
    public String downloadStu(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        StudentResource res = resourceService.findByIdStu(id);
        if (res == null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("资源不存在");
            return null;
        }
        File file = new File(res.getPath());
        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("资源不存在");
            return null;
        }

        response.setContentType("application/octet-stream");
        response.addHeader("content-type", "octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName().split("_")[1], "UTF-8"));

        try (FileInputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, response.getOutputStream());
        }
        return null;
    }

}
