package org.gdpi.course.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码
 * @author zhf
 */
@Data
public class ValidationCode implements Serializable {
    /**
     * 验证码
     */
    private String code;
    /**
     * 有效时间
     */
    private LocalDateTime timeout = LocalDateTime.now();
}
