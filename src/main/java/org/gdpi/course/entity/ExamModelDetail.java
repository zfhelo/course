package org.gdpi.course.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zhf
 */
@Data()
@ToString(callSuper = true)
public class ExamModelDetail extends ExamModel{
    @Min(value = 0)
    private Integer singleNum = 0;
    @Min(value = 0)
    private Integer singleGrade = 0;
    @Min(value = 0)
    private Integer torfNum = 0;
    @Min(value = 0)
    private Integer torfGrade = 0;
    @Min(value = 0)
    private Integer gapNum = 0;
    @Min(value = 0)
    private Integer gapGrade = 0;
    @Min(value = 0)
    private Integer essayNum = 0;
    @Min(value = 0)
    private Integer essayGrade = 0;
    @NotNull
    private String courseNum;
}
