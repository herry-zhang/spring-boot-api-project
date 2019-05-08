package com.trade.training.model.dto.request.sys;

import com.trade.training.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 修改角色权限参数
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysRoleMenuRequestDTO {

    /**
     * 主键，自动递增
     */
    @NotNull(message = "角色ID不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "角色ID不能小于1", groups = {UpdateGroup.class})
    private Long roleId;

    /**
     * 菜单ID
     */
    @NotNull(message = "菜单ID不能为空", groups = {UpdateGroup.class})
    private Long[] menuIds;
}
