package org.gdpi.course.reponse;

/**
 * @author zhf
 */
public enum ResultCode {
    /**
     * success 2xx成功 4xx失败
     */
    SUCCESS(200,"成功"),ERROR(400,"失败");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
