package org.gdpi.course.service;

import org.gdpi.course.entity.StudentResource;
import org.gdpi.course.entity.TeacherResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author zhf
 */
public interface ResourceService {
    /**
     * 保存文件
     * @param studentResource
     */
    void saveFile(StudentResource studentResource, MultipartFile file) throws IOException;

    /**
     * 保存文件
     * @param teacherResource
     */
    void saveFile(TeacherResource teacherResource, MultipartFile file) throws IOException;

    /**
     * 查找该学生加入课程的所有资源
     * @param sid
     * @return
     */
    List<StudentResource> findResourcesStu(Integer sid);

    /**
     * 查找该教师所有课程的学生资源
     * @param tid  教师id
     * @return
     */
    List<StudentResource> findResourcesTea(Integer tid);

    /**
     * 通过id查找资源
     * @param id
     * @return
     */
    StudentResource findByIdStu(Integer id);
    /**
     * 通过id查找资源
     * @param id
     * @return
     */
    TeacherResource findByIdTea(Integer id);

    /**
     * 删除资源
     * @param id
     */
    void deleteByIdStu(Integer id);
    /**
     * 删除资源
     * @param id
     */
    void deleteByIdTea(Integer id);

    /**
     * 通过课程id查找资源
     * @param cid
     * @return
     */
    List<StudentResource> findByCourseIdStu(Integer cid);
    /**
     * 通过课程id查找资源
     * @param cid
     * @return
     */
    List<TeacherResource> findByCourseIdTea(Integer cid);

    void deleteByAllForStu(Integer cid, Integer sid);
}
