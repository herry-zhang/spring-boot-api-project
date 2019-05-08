package com.trade.training.config;

/**
 * 常量
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public class ConfigConst {

    /**
     * 是否测试环境
     */
    public static final Boolean IS_TEST = false;

    /**
     * 系统管理员角色名称
     */
    public static final String SYS_ADMIN_ROLE_NAME = "admin";

    /**
     * 阿里云
     */
    public static final String ACCESS_ID = "";

    /**
     * 阿里云
     */
    public static final String ACCESS_KEY = "";

    /**
     * 阿里云CDN
     */
    public static final String AUTH_KEY = "";

    /**
     * 阿里云OSS
     */
    public static final String ENDPOINT = "";

    /**
     * 阿里云OSS
     */
    public static final String BUCKET_VIDEO = IS_TEST ? "test-video" : "video";

    /**
     * 阿里云OSS
     */
    public static final String BUCKET_TEMP = IS_TEST ? "test-temp" : "temp";

    /**
     * 图像访问地址域名
     */
    public static final String IMG_HOST = IS_TEST ? "http://test.img.yuming.com" : "https://img.yuming.com";

    /**
     * 视频访问地址域名
     */
    public static final String VIDEO_HOST = IS_TEST ? "http://test.video.yuming.com" : "https://video.yuming.com";

    /**
     * 系统用户默认头像
     */
    public static final String SYS_DEFAULT_AVATAR_URL = IMG_HOST + "/static/img/other/avatar-sys-user-default.jpg";
}
