package org.gdpi.course.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhf
 */
@Data
public class StudentHomework {
    private Integer id;
    private Integer homeworkId;
    private Integer stuId;
    private String answer;
    private Float grade;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time;
    private Student student;

    private Homework homework;
}
