package com.trade.training.model.dto.request.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 获取手机短信验证码
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class CommonSmsVerifyRequestDTO implements Serializable {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}", message = "手机号格式不正确")
    private String mobile;
}