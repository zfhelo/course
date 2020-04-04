package org.gdpi.course.entity;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author zhf
 */
@Data
public class EmailCode extends ValidationCode {
    /**
     * 接收者
     */
    private String to;

    /**
     * 简单验证消息
     * 随机6位字符串
     * @param to 接受人
     * @return
     */
    public static EmailCode simpleMaleCode(String to) {
        EmailCode emailCode = new EmailCode();
        emailCode.setTo(to);
        emailCode.setCode(RandomStringUtils.randomAlphabetic(6));
        return emailCode;
    }
}
