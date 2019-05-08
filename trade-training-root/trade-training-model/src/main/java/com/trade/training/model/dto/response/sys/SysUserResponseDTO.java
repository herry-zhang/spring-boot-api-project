package com.trade.training.model.dto.response.sys;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserResponseDTO {

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户昵称
     */
    String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    String avatarUrl;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态 -1 删除 0：禁用 1：正常
     */
    private Byte status;

    /**
     * 角色
     */
    Long[] roleList;
}
