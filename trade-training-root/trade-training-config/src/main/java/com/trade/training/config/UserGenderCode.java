package com.trade.training.config;

/**
 * 用户性别
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum UserGenderCode {
    NOT_SET(0, "未设定"),
    MAN(1, "男"),
    WOMAN(2, "女");

    private String message;
    private int code;

    UserGenderCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
