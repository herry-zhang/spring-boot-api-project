package com.trade.training.model.dto.request.sys;

import com.trade.training.common.validator.group.AddGroup;
import com.trade.training.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * 系统用户信息参数
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserRequestDTO {

    /**
     * 主键，自动递增
     */
    @NotNull(message = "用户ID不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "用户ID不能小于1", groups = {UpdateGroup.class})
    private Long userId;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空", groups = {AddGroup.class})
    @Size(min = 3, max = 30, message = "用户名称应该在3-30个字符之间", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户名称不能为空", groups = {AddGroup.class})
    @Size(max = 30, message = "用户昵称应该小于30个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String nickName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    @Pattern(regexp = "^(?![0-9]+$)[a-zA-Z0-9\\W_]{6,30}$", message = "密码不能为纯数字的6-30个英文字符或数字组合", groups = {AddGroup.class, UpdateGroup.class})
    private String password;

    /**
     * 用户头像
     */
    @Pattern(regexp = "^((ht|f)tps?):\\/\\/([\\w\\-]+(\\.[\\w\\-]+)*\\/)*[\\w\\-]+(\\.[\\w\\-]+)*\\/?(\\?([\\w\\-\\.,@?^=%&:\\/~\\+#]*)+)?", message = "用户头像格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String avatarUrl;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1\\d{10}", message = "手机号格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    /**
     * 状态 -1：删除 0：禁用 1：正常
     */
    @Min(value = -1, message = "用户状态不正确", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "用户状态不正确", groups = {AddGroup.class, UpdateGroup.class})
    private Byte status;
}
