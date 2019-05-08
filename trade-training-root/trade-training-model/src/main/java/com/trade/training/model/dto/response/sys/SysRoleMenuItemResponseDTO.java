package com.trade.training.model.dto.response.sys;

import lombok.Data;

import java.util.List;

/**
 * 角色菜单信息
 *
 * @author herry-zhang
 * @date 2018-8-20 12:02:04
 */
@Data
public class SysRoleMenuItemResponseDTO {

    /**
     * 主键，自动递增
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
    private Byte menuType;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否拥有权限
     */
    private Boolean authorized;

    /**
     * 子菜单
     */
    private List<SysRoleMenuItemResponseDTO> children;
}
