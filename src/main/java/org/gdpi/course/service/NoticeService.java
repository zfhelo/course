package org.gdpi.course.service;

import org.gdpi.course.entity.Notice;

import java.util.List;

/**
 * @author zhf
 */
public interface NoticeService {
    /**
     * 添加公告
     * @param notice
     */
    void addNotice(Notice notice);

    /**
     * 查询该学生所有课程的公告
     * @param sid
     * @return
     */
    List<Notice> findBySid(Integer sid);

    /**
     * 查询该教师所有课程的公告
     * @param tid
     * @return
     */
    List<Notice> findByTid(Integer tid);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    Notice findById(Integer id);
}
