package org.gdpi.course.exception;

/**
 * 没有该课程
 * @author zhf
 */
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String msg) {
        super(msg);
    }

    public CourseNotFoundException() {
        super("未找到该课程");
    }
}
