package org.gdpi.course.reponse;

import lombok.Getter;

import java.io.Serializable;

/**
 * 响应数据实体类
 * @author zhf
 */
@Getter
public class SimpleResponse implements Serializable {
    private Integer code;
    private String message;
    private Object data;


    /**
     * 无数据成功响应
     * @return
     */
    public static SimpleResponse success() {
        return success(null);
    }
    /**
     * 有数据成功响应
     * @return
     */
    public static SimpleResponse success(Object data) {
        SimpleResponse response = setResultStatus(ResultCode.SUCCESS, data);
        return response;
    }
    /**
     * 无数据失败响应
     * @return
     */
    public static SimpleResponse error() {
        return error(null);
    }
    /**
     * 有数据失败响应
     * @return
     */
    public static SimpleResponse error(Object data) {
        SimpleResponse response = setResultStatus(ResultCode.ERROR, data);
        return response;
    }

    public static SimpleResponse setResultStatus(ResultCode result, Object data) {
        SimpleResponse response = new SimpleResponse();
        response.code = result.getCode();
        response.message = result.getMessage();
        response.data = data;
        return response;
    }
}
