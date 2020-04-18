package org.gdpi.course.entity;

import lombok.Data;

/**
 * @author zhf
 */
@Data
public class StudentInvitation extends Invitation{
    private Integer stuId;
    private Student user;
}
