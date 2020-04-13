package org.gdpi.course.exception;

/**
 * @author zhf
 */
public class ExamPaperNotFoundException extends RuntimeException{
    public ExamPaperNotFoundException(String msg) {
        super(msg);
    }

    public ExamPaperNotFoundException() {
        super("试卷没有找到");
    }
}
