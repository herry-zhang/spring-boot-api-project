package com.trade.training.service.sys;

import com.trade.training.model.dto.response.sys.SysMenuItemResponseDTO;

import java.util.List;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
public interface SysMenuService {

    /**
     * 获取菜单列表
     * @return
     */
    List<SysMenuItemResponseDTO> getMenuList();
}
