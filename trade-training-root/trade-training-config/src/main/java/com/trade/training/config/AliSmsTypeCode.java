package com.trade.training.config;

/**
 * 阿里云发送短信类型
 *
 * @author herry-zhang
 * @date 2018-8-22 15:18:58
 */
public enum AliSmsTypeCode {
    CLOUD_REGISTER(1, "云用户注册"),
    FORGET_PASSWORD(2, "云用户忘记密码");
    private String message;
    private int code;

    AliSmsTypeCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    /**
     * 根据value返回枚举类型,主要在switch中使用
     * @param value
     * @return
     */
    public static AliSmsTypeCode getByValue(int value) {
        for (AliSmsTypeCode code : values()) {
            if (code.getCode() == value) {
                return code;
            }
        }
        return null;
    }

}
