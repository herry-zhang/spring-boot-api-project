package com.trade.training.model.dto.request.sys;

import com.trade.training.common.validator.group.AddGroup;
import com.trade.training.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 角色信息参数
 *
 * @author herry-zhang
 * @date 2018-08-04 12:59:38
 */
@Data
public class SysRoleRequestDTO {

    /**
     * 主键，自动递增
     */
    @NotNull(message = "角色ID不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "角色ID不能小于1", groups = {UpdateGroup.class})
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空", groups = {AddGroup.class})
    @Pattern(regexp = "^(?![0-9]+$)[a-zA-Z0-9]+", message = "角色名称只能是英文或者英文加数字", groups = {AddGroup.class, UpdateGroup.class})
    private String roleName;

    /**
     * 备注
     */
    @NotBlank(message = "角色名称不能为空", groups = {AddGroup.class})
    private String remark;
}
