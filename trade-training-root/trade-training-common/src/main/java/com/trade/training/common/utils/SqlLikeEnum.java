package com.trade.training.common.utils;

/**
 * 操作日志类型
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum SqlLikeEnum {
    LEFT("left", "左边%"),
    RIGHT("right", "右边%"),
    CUSTOM("custom", "定制"),
    DEFAULT("default", "两边%");

    private final String type;
    private final String desc;

    private SqlLikeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }
}
