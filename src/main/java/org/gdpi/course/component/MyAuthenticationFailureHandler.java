package org.gdpi.course.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gdpi.course.reponse.SimpleResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        SimpleResponse error = SimpleResponse.error("请检查登录身份或账号密码");
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
