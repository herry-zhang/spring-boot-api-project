package com.trade.training.model.dto.response;

import com.trade.training.config.ErrorCode;

import java.io.Serializable;

/**
 * 返回基础类
 *
 * @author herryzhang
 * @date 2018-7-24 14:59:54
 */
public class BaseResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 返回结果
     */
    private Object result;

    private BaseResponseDTO() {

    }

    private BaseResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private BaseResponseDTO(Integer code, Object result) {
        this.code = code;
        this.result = result;
    }

    private BaseResponseDTO(Integer code) {
        this.code = code;
    }

    public BaseResponseDTO(Object result) {
        this.result = result;
    }

    public static BaseResponseDTO error() {
        return error(ErrorCode.SC_INTERNAL_SERVER_ERROR.getCode(), ErrorCode.SC_INTERNAL_SERVER_ERROR.getMessage());
    }

    public static BaseResponseDTO error(String message) {
        return error(ErrorCode.SC_INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static BaseResponseDTO error(Integer code, String message) {
        return new BaseResponseDTO(code, message);
    }

    public static BaseResponseDTO success(String message) {
        return new BaseResponseDTO(ErrorCode.OK.getCode(), message);
    }

    public static BaseResponseDTO success(Object result) {
        return new BaseResponseDTO(ErrorCode.OK.getCode(), result);
    }

    public static BaseResponseDTO success() {
        return new BaseResponseDTO(ErrorCode.OK.getCode());
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
