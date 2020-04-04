package org.gdpi.course.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author zhf
 */
@Data
public class Course {
    private Integer id;
    private Integer teaId;
    @Pattern(regexp = "^[A-Za-z0-9]{6,12}$", message = "账号限制:6-12位数字或字母.")
    private String number;
    @Length(max = 30, message = "课程名限制30个字符")
    private String name;
    @Length(max = 255, message = "封面url限制255个字符")
    private String cover;
    @Length(max = 500, message = "描述限制50个字符")
    private String description;
    private Date time;
}
