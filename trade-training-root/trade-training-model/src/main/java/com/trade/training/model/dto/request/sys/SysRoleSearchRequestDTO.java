package com.trade.training.model.dto.request.sys;

import lombok.Data;

/**
 * 角色信息参数
 *
 * @author herry-zhang
 * @date 2018-08-04 15:05:54
 */
@Data
public class SysRoleSearchRequestDTO {

    /**
     * 主键，自动递增
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;
}
