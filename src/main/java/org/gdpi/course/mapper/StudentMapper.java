package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gdpi.course.entity.Student;

/**
 * @author zhf
 */
@Mapper
public interface StudentMapper {
    /**
     * 创建用户
     * @param student
     * @return 影响记录数
     */
    Integer addStudent(Student student);

    /**
     * 删除用户
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 通过账号查找用户
     * @param username
     * @return null or user
     */
    Student findByUsername(String username);
}
