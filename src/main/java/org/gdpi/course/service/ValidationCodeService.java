package org.gdpi.course.service;

import org.gdpi.course.entity.EmailCode;

import javax.mail.MessagingException;

/**
 * @author zhf
 */
public interface ValidationCodeService {

    /**
     * 发送邮件
     * @param to 接受人
     * @return
     */
    EmailCode sendEmailCode(String to) throws MessagingException;
}
