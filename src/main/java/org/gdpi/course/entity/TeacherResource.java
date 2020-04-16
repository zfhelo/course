package org.gdpi.course.entity;

import lombok.Data;

/**
 * @author zhf
 */
@Data
public class TeacherResource extends Resource{
    private Integer teaId;
    private Teacher user;
    private Course course;
}
