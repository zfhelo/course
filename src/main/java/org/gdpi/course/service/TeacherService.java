package org.gdpi.course.service;

import org.gdpi.course.entity.Teacher;
import org.gdpi.course.exception.UserAlreadyExistedException;

/**
 * @author zhf
 */
public interface TeacherService {

    /**
     * 创建用户
     * @param teacher
     * @throws UserAlreadyExistedException
     */
    void addTeacher(Teacher teacher) throws UserAlreadyExistedException;

    /**
     * 通过账号查找用户
     * @param username
     * @return
     */
    Teacher findByUsername(String username);

    /**
     * 更新用户
     * @param teacher
     */
    void updateById(Teacher teacher);
}
