package org.gdpi.course.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhf
 */
@Data
public class ExamPaper {

    private Integer id;
    private Integer stuId;
    private Integer modelId;
    private Float grade;
    private Boolean status;
    private Integer rule;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    private Student student;

    private ExamModel examModel;

    private List<SingleQuestion> singleQues;
    private List<GapFillingQuestion> gapQues;
    private List<EssayQuestion> essayQues;
    private List<TrueOrFalseQuestion> torfQues;
}

