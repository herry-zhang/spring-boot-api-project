package com.trade.training.model.dto.request.sys;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录参数
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class LoginRequestDTO {

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    String verificationCode;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    String userName;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    String password;
}
