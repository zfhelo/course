package org.gdpi.course.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @author zhf
 */
@Data
public class GradeTable {
    private Integer id;
    private Integer stuId;
    private Integer courseId;
    @Min(value = 0, message = "成绩不可以小于零")
    private Integer grade;
    @Min(value = 0, message = "成绩不可以小于零")
    private Integer regularGrade;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;

    private Student student;
}
