package org.gdpi.course.service.impl;

import org.gdpi.course.entity.StudentResource;
import org.gdpi.course.entity.TeacherResource;
import org.gdpi.course.mapper.ResourceMapper;
import org.gdpi.course.service.ResourceService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author zhf
 */
@Controller
@ConfigurationProperties(prefix = "file.save")
public class ResourceServiceImpl implements ResourceService {

    private String baseLocation = "";
    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public void saveFile(StudentResource studentResource, MultipartFile file) throws IOException {
        String path = save(file);
        studentResource.setPath(path);
        resourceMapper.saveFileStu(studentResource);
    }

    @Override
    public void saveFile(TeacherResource teacherResource, MultipartFile file) throws IOException {
        String path = save(file);
        teacherResource.setPath(path);
        resourceMapper.saveFileTea(teacherResource);
    }

    /**
     * 保存文件->返回路径
     * @param file
     * @return
     * @throws IOException
     */
    private String save(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String[] split = UUID.randomUUID().toString().split("-");
        String randomStr = split[split.length - 1];
        File base = new File(baseLocation);
        if (!base.exists()) {
            base.mkdirs();
        }
        File saveLocation = new File(baseLocation, randomStr +"_"+ name);
        file.transferTo(saveLocation);
        return saveLocation.getAbsolutePath();
    }


    public void setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
    }

    @Override
    public List<StudentResource> findResourcesStu(Integer sid) {
        return resourceMapper.findResourcesStu(sid);
    }

    @Override
    public StudentResource findByIdStu(Integer id) {
        return resourceMapper.findByIdStu(id);
    }

    @Override
    public void deleteByIdStu(Integer id) {
        resourceMapper.deleteByIdStu(id);
    }

    @Override
    public List<StudentResource> findByCourseIdStu(Integer cid) {
        return resourceMapper.findByCourseIdStu(cid);
    }

    @Override
    public List<TeacherResource> findByCourseIdTea(Integer cid) {
        return resourceMapper.findByCourseIdTea(cid);
    }

    @Override
    public List<StudentResource> findResourcesTea(Integer tid) {
        return resourceMapper.findResourcesTea(tid);
    }

    @Override
    public TeacherResource findByIdTea(Integer id) {
        return resourceMapper.findByIdTea(id);
    }

    @Override
    public void deleteByIdTea(Integer id) {
        resourceMapper.deleteByIdTea(id);
    }

    @Override
    public void deleteByAllForStu(Integer cid, Integer sid) {
        resourceMapper.deleteByAllForStu(cid, sid);
    }
}
