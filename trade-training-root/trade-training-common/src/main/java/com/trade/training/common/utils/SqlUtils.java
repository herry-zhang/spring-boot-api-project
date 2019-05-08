package com.trade.training.common.utils;

/**
 * SQL帮助类
 *
 * @author herryzhang
 * @date 2018-07-18 21:59:05
 */
public class SqlUtils {
    /**
     * 拼接Like
     *
     * @param str
     * @param type
     * @return
     */
    public static String concatLike(String str, SqlLikeEnum type) {
        StringBuilder builder = new StringBuilder(str.length() + 3);
        switch (type) {
            case LEFT:
                builder.append("%").append(str);
                break;
            case RIGHT:
                builder.append(str).append("%");
                break;
            case CUSTOM:
                builder.append(str);
                break;
            default:
                builder.append("%").append(str).append("%");
        }

        return builder.toString();
    }
}
