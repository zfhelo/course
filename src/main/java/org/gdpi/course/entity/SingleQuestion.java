package org.gdpi.course.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author zhf
 */
@Data
public class SingleQuestion {
    private Integer id;
    private Integer courseId;
    // 题目内容
    @NotBlank(message = "题目不能为空")
    private String title;

    // 选项一位正确答案
    @NotBlank(message = "选项不能为空")
    @Length(max = 500)
    private String choose1;

    @NotBlank(message = "选项不能为空")
    @Length(max = 500)
    private String choose2;

    @NotBlank(message = "选项不能为空")
    @Length(max = 500)
    private String choose3;

    @NotBlank(message = "选项不能为空")
    @Length(max = 500)
    private String choose4;

    @Range(min = 0, message = "分值不能小于零")
    private Integer grade;

    private Integer reference;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;

}
