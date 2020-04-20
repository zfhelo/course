package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Comment;
import org.gdpi.course.entity.StudentInvitation;
import org.gdpi.course.mapper.StudentInvitationMapper;
import org.gdpi.course.service.StudentInvitationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
public class StudentInvitationServiceImpl implements StudentInvitationService {

    @Resource
    private StudentInvitationMapper studentInvitationMapper;

    @Override
    public Integer addInvitation(StudentInvitation studentInvitation) {
        return studentInvitationMapper.addInvitation(studentInvitation);
    }

    @Override
    public List<StudentInvitation> findBySid(Integer sid) {
        return studentInvitationMapper.findBySid(sid);
    }

    @Override
    public List<StudentInvitation> findByCourseId(Integer courseId) {
        return studentInvitationMapper.findByCourseId(courseId);
    }

    @Override
    public StudentInvitation findById(Integer id) {
        return studentInvitationMapper.findById(id);
    }

    @Override
    public void addComment(Comment comment) {
        studentInvitationMapper.addComment(comment);
    }

    @Override
    public List<Comment> findByInvitationId(Integer invitationId) {
        return studentInvitationMapper.findByInvitationId(invitationId);
    }

    @Override
    public void deleteByAllForStu(Integer cid, Integer sid) {
        studentInvitationMapper.deleteByAllForStu(cid, sid);
    }
}
