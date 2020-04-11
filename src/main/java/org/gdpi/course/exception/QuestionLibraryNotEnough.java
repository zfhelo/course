package org.gdpi.course.exception;

/**
 * @author zhf
 */
public class QuestionLibraryNotEnough extends Exception {
    public QuestionLibraryNotEnough(String msg) {
        super(msg);
    }

    public QuestionLibraryNotEnough() {
        super("题库中的题目无法满足要求");
    }
}
