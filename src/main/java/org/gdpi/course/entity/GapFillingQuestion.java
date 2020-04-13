package org.gdpi.course.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhf
 */
@Data
@ToString(callSuper = true)
public class GapFillingQuestion extends Question {
    @NotBlank(message = "答案不能为空")
    @Length(max = 500)
    private String answer;

    private String userAnswer;

}
