package com.trade.training.model.dto.request.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 系统用户信息参数
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserSearchRequestDTO {

    /**
     * 用户ID
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
    @Min(value = -1, message = "用户状态不正确")
    @Max(value = 1, message = "用户状态不正确")
    private Byte status;

    /**
     * 最后登录时间 - 开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    /**
     * 最后登录时间 - 结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
}
