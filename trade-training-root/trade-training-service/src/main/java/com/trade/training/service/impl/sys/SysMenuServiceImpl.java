package com.trade.training.service.impl.sys;

import com.google.common.collect.Lists;
import com.trade.training.config.MenuTypeCode;
import com.trade.training.mapper.sys.SysMenuMapper;
import com.trade.training.mapper.sys.SysRoleMapper;
import com.trade.training.mapper.sys.SysRoleMenuMapper;
import com.trade.training.model.dto.response.sys.SysMenuItemResponseDTO;
import com.trade.training.model.entity.sys.*;
import com.trade.training.service.sys.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuDAO;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuDAO;

    @Autowired
    private SysRoleMapper sysRoleDAO;

    @Override
    public List<SysMenuItemResponseDTO> getMenuList() {
        SysMenuExample menuExample = new SysMenuExample();
        // menuExample.createCriteria().andMenuTypeNotEqualTo((byte) MenuTypeCode.BUTTON.getCode());
        List<SysMenu> sysMenuList = sysMenuDAO.selectByExample(menuExample);
        sysMenuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        for (int i = 0, j = sysMenuList.size(); i < j; i++) {
            SysMenu sysMenu = sysMenuList.get(i);
            sysMenu.setPath("/" + sysMenu.getPath().replaceAll(":", "/"));
            sysMenuList.set(i, sysMenu);
        }
        List<Long> menuIds = Lists.transform(sysMenuList, (SysMenu sysMenuItem) -> sysMenuItem.getMenuId());
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andMenuIdIn(menuIds);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuDAO.selectByExample(roleMenuExample);
        List<Long> roleIds = Lists.transform(sysRoleMenuList, (SysRoleMenu sysRoleMenuItem) -> sysRoleMenuItem.getRoleId());
        SysRoleExample roleExample = new SysRoleExample();
        roleExample.createCriteria().andRoleIdIn(roleIds);
        List<SysRole> sysRoleList = sysRoleDAO.selectByExample(roleExample);
        // 一级菜单，只找出来目录以及子菜单
        List<SysMenu> firstLevelMenu = sysMenuList.stream()
                .filter((SysMenu sysMenuItem) -> sysMenuItem.getParentId() == 0)
                .collect(Collectors.toList());
        List<SysMenuItemResponseDTO> menuResponseList = getMenuList(firstLevelMenu, sysMenuList, sysRoleMenuList, sysRoleList);
        // 把按钮也加入到一级菜单中，这样可以在前端直接判断对接口是否有权限，不需要触发之后才能知道
        List<SysMenu> buttonMenu = sysMenuList.stream()
                .filter((SysMenu sysMenuItem) -> sysMenuItem.getMenuType() == MenuTypeCode.BUTTON.getCode())
                .collect(Collectors.toList());
        List<SysMenuItemResponseDTO> menuButtonResponseList = getMenuList(buttonMenu, sysMenuList, sysRoleMenuList, sysRoleList);
        // 合并
        menuResponseList.addAll(menuButtonResponseList);
        return menuResponseList;
    }

    private List<SysMenuItemResponseDTO> getMenuList(List<SysMenu> firstLevelMenu, List<SysMenu> sysMenuList, List<SysRoleMenu> sysRoleMenuList, List<SysRole> sysRoleList) {
        List<SysMenuItemResponseDTO> menuResponseList = new ArrayList<>();
        for (SysMenu sysMenuItem : firstLevelMenu) {
            SysMenuItemResponseDTO sysMenuItemResponseDTO = new SysMenuItemResponseDTO();
            // BeanUtils.copyProperties(sysMenuItem, sysMenuItemResponseDTO);
            sysMenuItemResponseDTO.setId(sysMenuItem.getMenuId());
            sysMenuItemResponseDTO.setName(sysMenuItem.getMenuName());
            sysMenuItemResponseDTO.setIcon(sysMenuItem.getIcon());
            sysMenuItemResponseDTO.setOrderNum(sysMenuItem.getOrderNum());
            sysMenuItemResponseDTO.setType(sysMenuItem.getMenuType());
            sysMenuItemResponseDTO.setPath(sysMenuItem.getPath());
            sysMenuItemResponseDTO.setHideInMenu(sysMenuItem.getIsShow() == 0);
            // 角色菜单对应关系
            List<SysRoleMenu> firstRoleList = sysRoleMenuList.stream()
                    .filter((SysRoleMenu sysRoleMenuItem) -> sysRoleMenuItem.getMenuId().equals(sysMenuItem.getMenuId()))
                    .collect(Collectors.toList());
            // 角色ID
            List<Long> firstRoleResponseList = Lists.transform(firstRoleList, (SysRoleMenu sysRoleItem) -> sysRoleItem.getRoleId());
            // 角色详情列表
            List<SysRole> sysRoleResponseList = sysRoleList.stream()
                    .filter((SysRole sysRoleItem) -> firstRoleResponseList.contains(sysRoleItem.getRoleId()))
                    .collect(Collectors.toList());
            // 角色名称
            List<String> sysRoleNameResponseList = Lists.transform(sysRoleResponseList, (SysRole sysRoleItem) -> sysRoleItem.getRoleName());
            sysMenuItemResponseDTO.setAuthority(sysRoleNameResponseList.toArray(new String[sysRoleNameResponseList.size()]));
            // 子菜单
            List<SysMenu> secondLevelMenu = sysMenuList.stream()
                    .filter((SysMenu secondSysMenuItem) ->
                        !secondSysMenuItem.getPath().equals(sysMenuItemResponseDTO.getPath()) &&
                        secondSysMenuItem.getPath().indexOf(sysMenuItemResponseDTO.getPath()) == 0 &&
                        secondSysMenuItem.getMenuType() != MenuTypeCode.BUTTON.getCode()
                    )
                    .collect(Collectors.toList());
            sysMenuItemResponseDTO.setChildren(getMenuList(secondLevelMenu, sysMenuList, sysRoleMenuList, sysRoleList));
            menuResponseList.add(sysMenuItemResponseDTO);
        }
        return menuResponseList;
    }
}
