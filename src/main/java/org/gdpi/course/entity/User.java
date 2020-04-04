package org.gdpi.course.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

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
    private Date time;
}
