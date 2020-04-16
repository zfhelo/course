package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gdpi.course.entity.Teacher;

/**
 * @author zhf
 */
@Mapper
public interface TeacherMapper {

    /**
     * 创建用户
     * @param teacher
     * @return 影响记录数
     */
    Integer addTeacher(Teacher teacher);

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
    Teacher findByUsername(String username);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    Teacher findById(Integer id);

    /**
     * 更新用户
     * @param teacher
     */
    void updateById(Teacher teacher);
}
