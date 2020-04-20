package org.gdpi.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Notice;
import org.gdpi.course.entity.Student;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.NoticeService;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author zhf
 */
@RestController
public class NoticeController {
    @Resource
    private NoticeService noticeService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;
    @Resource
    private CourseService courseService;

    /**
     * 发布公告
     * @param notice
     * @param result
     * @param userDetails
     * @return
     */
    @PostMapping("/tea/notice")
    public SimpleResponse addNotice(@RequestBody Notice notice, BindingResult result,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        if (result.hasErrors()) {
            return SimpleResponse.error("数据不符合规范");
        }

        Course course = courseService.findByNumber(notice.getCourseNumber());
        if (course == null) {
            return SimpleResponse.error("未知课程");
        }
        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        if (courseService.findByIdAndTeaId(course.getId(), tea.getId()) == null) {
            return SimpleResponse.error("没有权限");
        }
        notice.setCourseId(course.getId());
        if (notice.getSaveDay() != 0) {
            notice.setOverdueTime(LocalDateTime.now().plusDays(notice.getSaveDay()));
        }
        noticeService.addNotice(notice);

        return SimpleResponse.success();
    }

    /**
     * 学生查看所有公告
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/stu/notice")
    public SimpleResponse listNoticeStu(@RequestParam(defaultValue = "1") Integer page,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        Student stu = studentService.findByUsername(userDetails.getUsername());

        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> noticeService.findBySid(stu.getId()));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 教师查看所有公告
     * @param page
     * @param userDetails
     * @return
     */
    @GetMapping("/tea/notice")
    public SimpleResponse listNoticeTea(@RequestParam(defaultValue = "1") Integer page,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        Teacher tea = teacherService.findByUsername(userDetails.getUsername());

        PageInfo<Object> pageInfo = PageHelper.startPage(page, 10).doSelectPageInfo(() -> noticeService.findByTid(tea.getId()));

        return SimpleResponse.success(pageInfo);
    }

    /**
     * 查看公告
     * @param id
     * @return
     */
    @GetMapping("/public/notice/{id:\\d+}")
    public SimpleResponse getNotice(@PathVariable Integer id) {
        Notice notice = noticeService.findById(id);
        return SimpleResponse.success(notice);
    }

    /**
     * 获取新闻资源
     * @return
     * @throws IOException
     */
    @GetMapping("/public/news")
    public String news() throws IOException {
        String result = "";
        //1.打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.声明get请求
        HttpGet httpGet = new HttpGet("http://api.avatardata.cn/TouTiao/Query?key=4fbf93cea224428887e26cffff1c9a16&type=guoji");
        //3.发送请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4.判断状态码
        if(response.getStatusLine().getStatusCode()==200){
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            result = httpClient.execute(httpGet, responseHandler);
        }
        //5.关闭资源
        response.close();
        httpClient.close();
        return result;
    }
}
