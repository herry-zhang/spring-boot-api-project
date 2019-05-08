package com.trade.training.admin.controller.sys;

import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.service.sys.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author herry-zhang
 * @date 2018-7-16 19:20:38
 */
@RestController
@RequestMapping("/sys")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取菜单树
     *
     * @return 菜单列表
     */
    @GetMapping("/menu/list")
    public BaseResponseDTO getMenuTree() {
        return BaseResponseDTO.success(sysMenuService.getMenuList());
    }
}
