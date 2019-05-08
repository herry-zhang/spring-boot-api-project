package com.trade.training.config;

/**
 * 消息状态
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum YesOrNoCode {
    NO(0, "否，无，未读，未处理"),
    YES(1, "是，有，已读，已处理");

    private String message;
    private int code;

    YesOrNoCode(int code, String message) {
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
