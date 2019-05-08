package com.trade.training.model.dto.response.sys;

import lombok.Data;

import java.util.List;

/**
 * 菜单以及权限信息
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysMenuItemResponseDTO {
    /**
     * 主键，自动递增
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
    private Byte type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 拥有权限的角色
     */
    private String[] authority;

    /**
     * 是否在菜单中显示
     */
    private Boolean hideInMenu;

    /**
     * 子菜单
     */
    private List<SysMenuItemResponseDTO> children;

}
