package org.gdpi.course.exception;

/**
 * @author zhf
 */
public class UserAlreadyExistedException extends RuntimeException {
    public UserAlreadyExistedException(String msg) {
        super(msg);
    }

    public UserAlreadyExistedException() {
        super("账号已存在");
    }
}
