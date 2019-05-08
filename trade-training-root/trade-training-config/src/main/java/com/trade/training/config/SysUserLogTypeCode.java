package com.trade.training.config;

/**
 * 操作日志类型
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum SysUserLogTypeCode {
    LOGIN(1, "登录系统"),
    SENT_TASK(2, "发送任务"),
    SENT_WORK(3, "发送运营日志"),
    SENT_MESSAGE(4, "发送消息"),
    UPDATE_MESSAGE(5, "修改消息"),
    UPDATE_TASK(6,"修改任务");
    private String message;
    private int code;

    SysUserLogTypeCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }


    public static SysUserLogTypeCode getByValue(int value) {
        for (SysUserLogTypeCode code : values()) {
            if (code.getCode() == value) {
                return code;
            }
        }
        return null;
    }
}
