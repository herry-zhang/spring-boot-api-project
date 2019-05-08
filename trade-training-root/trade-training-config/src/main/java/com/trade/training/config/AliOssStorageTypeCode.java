package com.trade.training.config;

/**
 * 阿里云对象文件类型
 *
 * @author herry-zhang
 * @date 2018-8-22 15:18:58
 */
public enum AliOssStorageTypeCode {
    SYS_AVATAR(1, "系统用户头像");

    private String message;
    private int code;

    AliOssStorageTypeCode(int code, String message) {
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
    public static AliOssStorageTypeCode getByValue(int value) {
        for (AliOssStorageTypeCode code : values()) {
            if (code.getCode() == value) {
                return code;
            }
        }
        return null;
    }

}
