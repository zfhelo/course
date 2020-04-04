package org.gdpi.course.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gdpi.course.reponse.SimpleResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhf
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        for (GrantedAuthority permissions : authentication.getAuthorities()) {
            // 教师
            if ( permissions.getAuthority().equals("ROLE_TEA")){
                SimpleResponse success = SimpleResponse.success("tea/index");
                response.getWriter().write(objectMapper.writeValueAsString(success));
                return;
            }
        }
        // 学生登录跳转
        SimpleResponse success = SimpleResponse.success("stu/index");
        response.getWriter().write(objectMapper.writeValueAsString(success));

    }
}
