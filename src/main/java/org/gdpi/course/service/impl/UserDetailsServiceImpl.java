package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Student;
import org.gdpi.course.entity.Teacher;
import org.gdpi.course.service.StudentService;
import org.gdpi.course.service.TeacherService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhf
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String[] loginUser = s.split("\\$");
        String password = "";
        List<GrantedAuthority> permissions = new ArrayList<>();
        // 教师登录
        if ("tea".equals(loginUser[1])) {

            Teacher tea = teacherService.findByUsername(loginUser[0]);

            if (tea != null) {
                password = tea.getPassword();
                permissions.add(new SimpleGrantedAuthority("ROLE_TEA"));
            }

        } else if ("stu".equals(loginUser[1])){

            Student stu = studentService.findByUsername(loginUser[0]);

            if (stu != null) {
                password = stu.getPassword();
                permissions.add(new SimpleGrantedAuthority("ROLE_STU"));
            }
        }
        return new User(loginUser[0], password,permissions);
    }
}
