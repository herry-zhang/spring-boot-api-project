package com.trade.training.model.dto.response.sys;

import lombok.Data;

/**
 * 登录用户简要信息
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class LoginSysUserResponseDTO {

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 用户昵称
     */
    String nickName;

    /**
     * 用户头像
     */
    String avatarUrl;

    /**
     * 用户消息数目
     */
    Integer notifyCount;

    /**
     * 角色
     */
    String[] roleList;
}
