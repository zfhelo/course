package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Comment;
import org.gdpi.course.entity.TeacherInvitation;
import org.gdpi.course.mapper.TeacherInvitationMapper;
import org.gdpi.course.service.TeacherInvitationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
public class TeacherInvitationServiceImpl implements TeacherInvitationService {

    @Resource
    private TeacherInvitationMapper teacherInvitationMapper;

    @Override
    public Integer addInvitation(TeacherInvitation teacherInvitation) {
        return teacherInvitationMapper.addInvitation(teacherInvitation);
    }

    @Override
    public List<TeacherInvitation> findByTid(Integer sid) {
        return teacherInvitationMapper.findByTid(sid);
    }

    @Override
    public List<TeacherInvitation> findByCourseId(Integer courseId) {
        return teacherInvitationMapper.findByCourseId(courseId);
    }

    @Override
    public TeacherInvitation findById(Integer id) {
        return teacherInvitationMapper.findById(id);
    }

    @Override
    public void addComment(Comment comment) {
        teacherInvitationMapper.addComment(comment);
    }

    @Override
    public List<Comment> findByInvitationId(Integer invitationId) {
        return teacherInvitationMapper.findByInvitationId(invitationId);
    }
}
