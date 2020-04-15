package org.gdpi.course.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhf
 */
@Data
public class Course {
    private Integer id;
    private Integer teaId;
    @Pattern(regexp = "^[A-Za-z0-9]{6,12}$", message = "课程号限制:6-12位数字或字母.")
    private String number;
    @Length(max = 30, message = "课程名限制30个字符")
    private String name;
    @Length(max = 255, message = "封面url限制255个字符")
    private String cover;
    @Length(max = 500, message = "描述限制50个字符")
    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;
    // 学员数
    private Integer stuNum;


    private Teacher teacher;
    // 拓展属性
    private Integer published;
    private Integer overdue;
    private Integer finished;
    private List<Homework> homework;
    private List<Homework> submitHomework;
    private List<Homework> overdueHomework;
}
