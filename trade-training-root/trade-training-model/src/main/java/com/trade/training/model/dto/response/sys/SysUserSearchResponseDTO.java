package com.trade.training.model.dto.response.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 系统用户信息
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserSearchResponseDTO {

    /**
     * 主键，自动递增
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态 -1：删除 0：禁用 1：正常
     */
    private Byte status;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 角色
     */
    private List<String> roleList;
}
