package org.gdpi.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gdpi.course.entity.Comment;
import org.gdpi.course.entity.StudentInvitation;

import java.util.List;

/**
 * @author zhf
 */
@Mapper
public interface StudentInvitationMapper {
    /**
     * 发表帖子
     * @param studentInvitation
     * @return
     */
    Integer addInvitation(StudentInvitation studentInvitation);

    /**
     * 通过学生id查找所有帖子
     * @param sid
     * @return
     */
    List<StudentInvitation> findBySid(Integer sid);

    /**
     * 通过课程id查找
     * @param courseId
     * @return
     */
    List<StudentInvitation> findByCourseId(Integer courseId);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    StudentInvitation findById(Integer id);

    /**
     * 发布评论
     * @param comment
     */
    void addComment(Comment comment);

    /**
     * 查找该帖子下的所有评论
     * @param invitationId
     * @return
     */
    List<Comment> findByInvitationId(Integer invitationId);
}
