package org.gdpi.course.service.impl;

import org.gdpi.course.entity.Notice;
import org.gdpi.course.mapper.NoticeMapper;
import org.gdpi.course.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhf
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public void addNotice(Notice notice) {
        noticeMapper.addNotice(notice);
    }

    @Override
    public List<Notice> findBySid(Integer sid) {
        return noticeMapper.findBySid(sid);
    }

    @Override
    public List<Notice> findByTid(Integer tid) {
        return noticeMapper.findByTid(tid);
    }

    @Override
    public Notice findById(Integer id) {
        return noticeMapper.findById(id);
    }
}
