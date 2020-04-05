package org.gdpi.course.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhf
 */
@Data
public class User implements Serializable {
    private Integer id;
    @Pattern(regexp = "^[A-Za-z0-9]{6,12}$", message = "账号限制:6-12位数字或字母.")
    private String username;
    @Length(max = 30, min = 1)
    private String nickname;
    private String password;
    @Length(max = 255, message = "头像url限制255个字符")
    private String photo;
    @Email
    private String email;
    private String phone;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;
}
