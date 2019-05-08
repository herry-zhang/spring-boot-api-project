package com.trade.training.admin.controller.sys;

import com.trade.training.admin.shiro.ShiroUtils;
import com.trade.training.model.dto.request.sys.SysRoleDeleteRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleMenuRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleSearchRequestDTO;
import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.service.sys.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色相关
 *
 * @author herry-zhang
 * @date 2018-08-04 11:05:17
 */
@RestController
@RequestMapping("/sys")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 添加角色
     */
    @PostMapping("/role/add")
    @RequiresPermissions("sys:role:add")
    public BaseResponseDTO addRole(@RequestBody SysRoleRequestDTO sysRoleRequestDTO) {
        sysRoleService.addRole(sysRoleRequestDTO, ShiroUtils.getUserID());
        return BaseResponseDTO.success("角色添加成功");
    }

    /**
     * 修改角色
     */
    @PostMapping("/role/update")
    @RequiresPermissions("sys:role:update")
    public BaseResponseDTO updateRole(@RequestBody SysRoleRequestDTO sysRoleRequestDTO) {
        sysRoleService.updateRole(sysRoleRequestDTO, ShiroUtils.getUserID());
        return BaseResponseDTO.success("角色信息修改成功");
    }

    /**
     * 删除角色
     */
    @PostMapping("/role/delete")
    @RequiresPermissions("sys:role:delete")
    public BaseResponseDTO deleteRole(@RequestBody SysRoleDeleteRequestDTO sysRoleDeleteRequestDTO) {
        sysRoleService.deleteRole(sysRoleDeleteRequestDTO, ShiroUtils.getUserID());
        return BaseResponseDTO.success("删除角色成功");
    }

    /**
     * 获取角色列表
     */
    @PostMapping("/role/list")
    @RequiresPermissions("sys:role:list")
    public BaseResponseDTO getRoleList(@RequestBody BasePageRequestDTO<SysRoleSearchRequestDTO> basePageRequestDTO) {
        return BaseResponseDTO.success(sysRoleService.getRoleList(basePageRequestDTO));
    }

    /**
     * 获取角色权限列表
     */
    @GetMapping("/role/auth/{role_id}")
    @RequiresPermissions("sys:role:auth")
    public BaseResponseDTO getRoleMenuList(@PathVariable("role_id") Long roleId) {
        return BaseResponseDTO.success(sysRoleService.getRoleMenuList(roleId));
    }

    /**
     * 修改角色权限
     */
    @PostMapping("/role/auth")
    @RequiresPermissions("sys:role:auth")
    public BaseResponseDTO updateRoleMenuList(@RequestBody SysRoleMenuRequestDTO sysRoleMenuRequestDTO) {
        sysRoleService.updateRoleMenuList(sysRoleMenuRequestDTO, ShiroUtils.getUserID());
        return BaseResponseDTO.success("角色权限修改成功");
    }
}
