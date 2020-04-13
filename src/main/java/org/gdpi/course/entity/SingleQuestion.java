package org.gdpi.course.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhf
 */
@Data
public class SingleQuestion extends Question{
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

    private String userAnswer;
}
