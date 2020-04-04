package org.gdpi.course.service;

import org.gdpi.course.entity.Teacher;

/**
 * @author zhf
 */
public interface TeacherService {
    /**
     * 创建用户
     * @param teacher
     */
    void addTeacher(Teacher teacher);

    /**
     * 通过账号查找用户
     * @param username
     * @return
     */
    Teacher findByUsername(String username);
}
