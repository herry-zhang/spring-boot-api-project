package com.trade.training.config;

/**
 * 用户状态
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum SysUserStatusCode {
    DELETE(-1, "账号已被删除"),
    LOCKED(0, "账号已被锁定"),
    NORMAL(1, "正常");

    private String message;
    private int code;

    SysUserStatusCode(int code, String message) {
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
