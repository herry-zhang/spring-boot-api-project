package com.trade.training.model.dto.response.sys;

import lombok.Data;

import java.util.Date;

/**
 * 角色信息
 *
 * @author herry-zhang
 * @date 2018-08-04 15:12:40
 */
@Data
public class SysRoleSearchResponseDTO {

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
