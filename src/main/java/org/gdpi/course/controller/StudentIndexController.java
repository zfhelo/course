package org.gdpi.course.controller;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.gdpi.course.entity.Course;
import org.gdpi.course.entity.Student;
import org.gdpi.course.reponse.SimpleResponse;
import org.gdpi.course.service.CourseService;
import org.gdpi.course.service.HomeworkService;
import org.gdpi.course.service.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author zhf
 */

@RestController
@RequestMapping("/stu")
public class StudentIndexController {
    @Resource
    private CourseService courseService;
    @Resource
    private StudentService studentService;
    @Resource
    private HomeworkService homeworkService;

    @GetMapping("/index/homework")
    public SimpleResponse getAllHomework(@AuthenticationPrincipal UserDetails userDetails) {
        Student stu = studentService.findByUsername(userDetails.getUsername());
        List<Course> courses = courseService.findCourseBySid(stu.getId());
        homeworkService.findAllHomework(courses, stu.getId());
        return SimpleResponse.success(courses);
    }


    private String[] keyword = {"大数据", "人工智能", "数据结构", "go"};
    private String[] expand={"进阶", "拓展"};

    @GetMapping("/recommend")
    public String[] recommend(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Student user = studentService.findByUsername(userDetails.getUsername());
        List<String> tags = courseService.findTags(user.getId());
        ArrayList<String> tags2 = new ArrayList<>(tags);
        Random random = new Random();
        for (int i = 0; i < tags2.size(); i++) {
            int index = random.nextInt(2);
            String result = tags2.get(i) + expand[index];
            tags2.set(i, result);
        }

        if (tags.size() < 2) {
            tags.addAll(Arrays.asList(keyword));
        }

        tags.addAll(tags2);
        Collections.shuffle(tags);

        String[] result = {getVideo(tags.get(0), random.nextInt(5) + 1), getVideo(tags.get(1), random.nextInt(5) + 1), getVideo(tags.get(2), random.nextInt(5) + 1)};

        return result;
    }

    private String getVideo(String keyword, Integer page) throws IOException {

        String result = "";
        //1.打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.声明get请求
        String url = "https://api.bilibili.com/x/web-interface/search/type?context=&page="+page+"&order=dm&keyword="+keyword+"&duration=0&tids_2=&search_type=video";
        HttpGet httpGet = new HttpGet(url);
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
