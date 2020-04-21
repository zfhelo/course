package org.gdpi.course.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhf
 */
@ControllerAdvice
@Slf4j
public class GlobalController {

    @ExceptionHandler(Exception.class)
    public ModelAndView customException(Exception e) {
        ModelAndView mv = new ModelAndView();
        log.error(e.getMessage());
        mv.setViewName("500");
        return mv;
    }
}
