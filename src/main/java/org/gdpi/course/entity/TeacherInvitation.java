package org.gdpi.course.entity;

import lombok.Data;

/**
 * @author zhf
 */
@Data
public class TeacherInvitation extends Invitation{
    private Integer teaId;
    private Teacher user;
}
