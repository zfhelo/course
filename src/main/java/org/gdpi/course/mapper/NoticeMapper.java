package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gdpi.course.entity.Notice;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface NoticeMapper {

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
     * 通过id查找
     * @param id
     * @return
     */
    Notice findById(Integer id);

    /**
     * 查询该学生所有课程的公告
     * @param tid
     * @return
     */
    List<Notice> findByTid(Integer tid);

}
