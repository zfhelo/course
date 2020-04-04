package org.gdpi.course.service;

import org.gdpi.course.entity.Student;

/**
 * @author zhf
 */
public interface StudentService {
    /**
     * 创建用户
     * @param student
     */
    void addStudent(Student student);

    /**
     * 通过账号查找用户
     * @param username
     * @return
     */
    Student findByUsername(String username);
}
