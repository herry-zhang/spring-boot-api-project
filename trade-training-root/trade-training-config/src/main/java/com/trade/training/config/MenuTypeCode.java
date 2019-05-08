package com.trade.training.config;

/**
 * 菜单类型
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum MenuTypeCode {
    CATALOG(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private String message;
    private int code;

    MenuTypeCode(int code, String message) {
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
