package com.trade.training.config;

/**
 * 设备来源
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum SourceCode {
    PC(1, "电脑端"),
    WX_APP(2, "微信小程序"),
    IOS(3, "IOS"),
    ANDROID(4, "Android"),
    WAP(5, "手机网页端");

    private String message;
    private int code;

    SourceCode(int code, String message) {
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
