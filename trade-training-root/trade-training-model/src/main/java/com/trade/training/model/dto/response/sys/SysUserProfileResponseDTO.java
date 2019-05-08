package com.trade.training.model.dto.response.sys;

import lombok.Data;

import java.util.Date;

/**
 * 登录用户的个人信息
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserProfileResponseDTO {

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 用户名称
     */
    String userName;

    /**
     * 用户昵称
     */
    String nickName;

    /**
     * 用户头像
     */
    String avatarUrl;

    /**
     * 邮箱
     */
    String email;

    /**
     * 手机号
     */
    String mobile;

    /**
     * 所属主管
     */
    String director;

    /**
     * 最后登录时间
     */
    Date lastLoginTime;

    /**
     * 角色
     */
    String[] roleList;
}
