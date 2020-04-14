package org.gdpi.course.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhf
 */
@Data
@ToString(callSuper = true)
public class EssayQuestion extends Question {
    private String userAnswer;
    private Float userGrade;
}
