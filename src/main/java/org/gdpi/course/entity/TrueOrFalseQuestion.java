package org.gdpi.course.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author zhf
 */
@Data
@ToString(callSuper = true)
public class TrueOrFalseQuestion extends Question {
    @NotNull
    private Boolean answer;
    private Boolean userAnswer;
}
