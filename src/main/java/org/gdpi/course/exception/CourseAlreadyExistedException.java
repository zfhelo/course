package org.gdpi.course.exception;

/**
 * @author zhf
 */
public class CourseAlreadyExistedException extends RuntimeException {
    public CourseAlreadyExistedException(String msg) {
        super(msg);
    }

    public CourseAlreadyExistedException() {
        super("已存在该课程");
    }
}
