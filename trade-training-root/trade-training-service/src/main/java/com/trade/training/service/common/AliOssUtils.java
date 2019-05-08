package com.trade.training.service.common;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.trade.training.config.AliOssStorageTypeCode;
import com.trade.training.config.ConfigConst;

import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.trade.training.config.ConfigConst.*;

/**
 * 阿里云OSS相关
 *
 * @author herry-zhang
 * @date 2018-8-25 13:48:26
 */
public class AliOssUtils {

    public static Map<String, String> getOssSign(Integer typeId) {
        String dir = "";
        String host = "";
        // 由于开启了CDN鉴权所以不能直接走自己的域名上传视频，要把阿里的OSS所在域名传给前端
        String action = "";
        long expireTime = 0;
        switch (AliOssStorageTypeCode.getByValue(typeId)) {
            case SYS_AVATAR:
                dir += "static/img/sys-avatar/";
                host = IMG_HOST;
                expireTime = 30;
                break;
            default:
                break;
        }
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        Map<String, String> respMap = new LinkedHashMap<>();
        try {
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            respMap.put("accessId", ACCESS_ID);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("action", action);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
        }
        return respMap;
    }

    public static String getOssSignTempURL(String url) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        Date expiration = new Date(System.currentTimeMillis() + 60 * 30 * 1000);
        URL signUrl = ossClient.generatePresignedUrl(BUCKET_TEMP, url.substring(url.indexOf(".com/") + 5), expiration);
        ossClient.shutdown();
        return signUrl.toString();
    }


    public static void deleteOssSingleAnnex(String url) {
        String endpoint = ConfigConst.ENDPOINT;
        String accessKeyId = ConfigConst.ACCESS_ID;
        String accessKeySecret = ConfigConst.ACCESS_KEY;
        String bucketName = ConfigConst.BUCKET_TEMP;
        String objectName = url.substring(url.indexOf(".com/") + 5);
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }

    public static void deleteOssAnnexList(List<String> keys) {
        String endpoint = ConfigConst.ENDPOINT;
        String accessKeyId = ConfigConst.ACCESS_ID;
        String accessKeySecret = ConfigConst.ACCESS_KEY;
        String bucketName = ConfigConst.BUCKET_TEMP;
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 执行批量删除操作
        ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        // 关闭OSSClient。
        ossClient.shutdown();
    }


}
