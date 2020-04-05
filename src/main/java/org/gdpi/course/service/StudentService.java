package org.gdpi.course.service;

import org.gdpi.course.entity.Student;
import org.gdpi.course.exception.UserAlreadyExistedException;

/**
 * @author zhf
 */
public interface StudentService {

    /**
     * 创建用户
     * @param student
     * @throws UserAlreadyExistedException
     */
    void addStudent(Student student) throws UserAlreadyExistedException;

    /**
     * 通过账号查找用户
     * @param username
     * @return
     */
    Student findByUsername(String username);
}
