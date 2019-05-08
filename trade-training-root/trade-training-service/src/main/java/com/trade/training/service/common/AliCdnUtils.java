package com.trade.training.service.common;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Date;

import static com.trade.training.config.ConfigConst.*;

/**
 * 阿里云CDN相关
 *
 * @author herry-zhang
 * @date 2018-9-14 16:35:08
 */
public class AliCdnUtils {

    public static String getCdnSignURL(String url) {
        try {
            // Long expiration = (System.currentTimeMillis() - 1797 * 1000) / 1000;
            Long expiration = (System.currentTimeMillis()) / 1000;
            String timestamp = Long.toHexString(expiration);
            String md5Str = AUTH_KEY + url.replace(VIDEO_HOST, "") + timestamp;
            String md5StrNew = new Md5Hash(md5Str).toHex();
            return url + "?KEY1=" + md5StrNew + "&KEY2=" + timestamp;
        } catch (Exception e){
        }
        return url;
    }
}
