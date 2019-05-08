package com.trade.training.service.sys;

import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleDeleteRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleMenuRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleSearchRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.sys.SysRoleMenuItemResponseDTO;
import com.trade.training.model.dto.response.sys.SysRoleSearchResponseDTO;
import com.trade.training.model.entity.sys.SysRole;

import java.util.List;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
public interface SysRoleService {


    /**
     * 根据角色名查询
     *
     * @param userRole 角色名称
     * @return 角色信息
     */
    SysRole queryByRoleName(String userRole);

    /**
     * 添加角色
     *
     * @param sysRoleRequestDTO 角色
     * @param createRoleId      创建人ID
     */
    void addRole(SysRoleRequestDTO sysRoleRequestDTO, Long createRoleId);

    /**
     * 修改角色信息
     *
     * @param sysRoleRequestDTO 角色
     * @param updateRoleId      修改人ID
     */
    void updateRole(SysRoleRequestDTO sysRoleRequestDTO, Long updateRoleId);

    /**
     * 删除角色信息
     *
     * @param sysRoleDeleteRequestDTO 角色ID
     * @param updateRoleId            修改人ID
     */
    void deleteRole(SysRoleDeleteRequestDTO sysRoleDeleteRequestDTO, Long updateRoleId);

    /**
     * 搜索角色列表
     *
     * @param basePageRequestDTO 搜索条件
     * @return 角色列表
     */
    BasePageResponseListDTO<SysRoleSearchResponseDTO> getRoleList(BasePageRequestDTO<SysRoleSearchRequestDTO> basePageRequestDTO);


    /**
     * 根据角色ID获取权限菜单
     *
     * @param roleId 角色ID
     * @return 权限菜单列表
     */
    List<SysRoleMenuItemResponseDTO> getRoleMenuList(Long roleId);

    /**
     * 修改角色权限菜单
     *
     * @param sysRoleMenuRequestDTO 角色ID
     * @param updateRoleId          修改人ID
     */
    void updateRoleMenuList(SysRoleMenuRequestDTO sysRoleMenuRequestDTO, Long updateRoleId);
}
