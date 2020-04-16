package org.gdpi.course.entity;

import lombok.Data;

/**
 * @author zhf
 */
@Data
public class StudentResource extends Resource{
    private Integer stuId;
    private Student user;
    private Course course;
}
