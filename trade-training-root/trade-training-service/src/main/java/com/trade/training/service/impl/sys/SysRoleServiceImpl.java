package com.trade.training.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.trade.training.common.exception.CustomException;
import com.trade.training.common.utils.SqlLikeEnum;
import com.trade.training.common.utils.SqlUtils;
import com.trade.training.common.validator.ValidatorUtils;
import com.trade.training.common.validator.group.AddGroup;
import com.trade.training.common.validator.group.UpdateGroup;
import com.trade.training.config.ErrorCode;
import com.trade.training.mapper.sys.SysMenuMapper;
import com.trade.training.mapper.sys.SysRoleMapper;
import com.trade.training.mapper.sys.SysRoleMenuMapper;
import com.trade.training.mapper.sys.SysUserRoleMapper;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleDeleteRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleMenuRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleRequestDTO;
import com.trade.training.model.dto.request.sys.SysRoleSearchRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.BasePageResponsePaginationDTO;
import com.trade.training.model.dto.response.sys.SysRoleMenuItemResponseDTO;
import com.trade.training.model.dto.response.sys.SysRoleSearchResponseDTO;
import com.trade.training.model.entity.sys.*;
import com.trade.training.service.sys.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.trade.training.config.ConfigConst.SYS_ADMIN_ROLE_NAME;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleMapper sysRoleDAO;
    @Autowired
    private SysUserRoleMapper sysUserRoleDAO;
    @Autowired
    private SysMenuMapper sysMenuDAO;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuDAO;


    @Override
    public SysRole queryByRoleName(String roleName) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.createCriteria().andRoleNameEqualTo(roleName);
        List<SysRole> list = sysRoleDAO.selectByExample(sysRoleExample);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void addRole(SysRoleRequestDTO sysRoleRequestDTO, Long createUserId) {
        ValidatorUtils.validateEntity(sysRoleRequestDTO, AddGroup.class);
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleRequestDTO, sysRole);
        if (this.queryByRoleName(sysRole.getRoleName()) != null) {
            throw new CustomException(ErrorCode.SYS_ROLE_NAME_EXISTED.getCode(), ErrorCode.SYS_ROLE_NAME_EXISTED.getMessage());
        }
        sysRole.setCreateUserId(createUserId);
        sysRole.setCreateTime(new Date());
        sysRoleDAO.insertSelective(sysRole);
    }

    @Override
    public void updateRole(SysRoleRequestDTO sysRoleRequestDTO, Long createRoleId) {
        ValidatorUtils.validateEntity(sysRoleRequestDTO, UpdateGroup.class);
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleRequestDTO, sysRole);
        SysRole role = sysRoleDAO.selectByPrimaryKey(sysRole.getRoleId());
        if (role == null) {
            throw new CustomException(ErrorCode.SYS_ROLE_NOT_FOUND.getCode(), ErrorCode.SYS_ROLE_NOT_FOUND.getMessage());
        } else if ("".equals(sysRole.getRoleName()) || "".equals(sysRole.getRemark())) {
            throw new CustomException(ErrorCode.SYS_ROLE_NOT_UPDATE.getCode(), ErrorCode.SYS_ROLE_NOT_UPDATE.getMessage() + "，字段不能为空");
        } else if (StringUtils.isNotBlank(sysRole.getRoleName()) && role.getRoleName().equals(SYS_ADMIN_ROLE_NAME)) {
            throw new CustomException(ErrorCode.SYS_ROLE_NOT_UPDATE.getCode(), ErrorCode.SYS_ROLE_NOT_UPDATE.getMessage() + "，管理员角色名称不能修改");
        } else if (StringUtils.isNotBlank(sysRole.getRoleName()) && sysRole.getRoleName() != role.getRoleName() && this.queryByRoleName(sysRole.getRoleName()) != null) {
            throw new CustomException(ErrorCode.SYS_ROLE_NAME_EXISTED.getCode(), ErrorCode.SYS_ROLE_NAME_EXISTED.getMessage());
        }
        sysRole.setUpdateUserId(createRoleId);
        sysRole.setUpdateTime(new Date());
        sysRoleDAO.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public void deleteRole(SysRoleDeleteRequestDTO sysRoleDeleteRequestDTO, Long updateUserId) {
        ValidatorUtils.validateEntity(sysRoleDeleteRequestDTO);
        Long[] roleIds = sysRoleDeleteRequestDTO.getRoleIds();
        SysUserRoleExample userRoleExample = new SysUserRoleExample();
        userRoleExample.createCriteria().andRoleIdIn(Arrays.asList(roleIds));
        if (sysUserRoleDAO.selectByExample(userRoleExample).size() > 0) {
            throw new CustomException(ErrorCode.SYS_ROLE_USER_FOUND.getCode(), ErrorCode.SYS_ROLE_USER_FOUND.getMessage());
        }
        SysRoleExample roleExample = new SysRoleExample();
        roleExample.createCriteria().andRoleIdIn(Arrays.asList(roleIds));
        List<SysRole> sysRoles = sysRoleDAO.selectByExample(roleExample);
        for (SysRole sysRoleItem : sysRoles) {
            if(sysRoleItem.getRoleName().equals(SYS_ADMIN_ROLE_NAME)) {
                throw new CustomException(ErrorCode.SYS_ROLE_NOT_DELETE.getCode(), ErrorCode.SYS_ROLE_NOT_DELETE.getMessage());
            }
        }
        sysRoleDAO.deleteByExample(roleExample);
    }

    @Override
    public BasePageResponseListDTO<SysRoleSearchResponseDTO> getRoleList(BasePageRequestDTO<SysRoleSearchRequestDTO> basePageRequestDTO) {
        ValidatorUtils.validateEntity(basePageRequestDTO);
        BasePageResponseListDTO<SysRoleSearchResponseDTO> userResponse = new BasePageResponseListDTO<>();
        PageHelper.startPage(basePageRequestDTO.getPageNum(), basePageRequestDTO.getPageSize());
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        SysRoleSearchRequestDTO sysUserSearch = basePageRequestDTO.getSearch();
        if(sysUserSearch != null) {
            if (sysUserSearch.getRoleId() != null) {
                criteria.andRoleIdEqualTo(sysUserSearch.getRoleId());
            }
            if (StringUtils.isNotBlank(sysUserSearch.getRoleName())) {
                criteria.andRoleNameLike(SqlUtils.concatLike(sysUserSearch.getRoleName(), SqlLikeEnum.DEFAULT));
            }
            if (StringUtils.isNotBlank(sysUserSearch.getRemark())) {
                criteria.andRemarkLike(SqlUtils.concatLike(sysUserSearch.getRemark(), SqlLikeEnum.DEFAULT));
            }
        }
        List<SysRole> roleList = sysRoleDAO.selectByExample(example);
        List<SysRoleSearchResponseDTO> roleResponseList = Lists.transform(roleList, (SysRole sysRoleItem) -> {
            SysRoleSearchResponseDTO sysRoleSearchResponseDTO = new SysRoleSearchResponseDTO();
            BeanUtils.copyProperties(sysRoleItem, sysRoleSearchResponseDTO);
            return sysRoleSearchResponseDTO;
        });
        BasePageResponsePaginationDTO pagination = new BasePageResponsePaginationDTO();
        pagination.setCurrent(basePageRequestDTO.getPageNum());
        pagination.setPageSize(basePageRequestDTO.getPageSize());
        pagination.setTotal(PageHelper.count(() -> sysRoleDAO.selectByExample(example)));
        userResponse.setPagination(pagination);
        userResponse.setList(roleResponseList);
        return userResponse;
    }

    @Override
    public List<SysRoleMenuItemResponseDTO> getRoleMenuList(Long roleId) {
        SysMenuExample menuExample = new SysMenuExample();
        List<SysMenu> sysMenuList = sysMenuDAO.selectByExample(menuExample);
        sysMenuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andRoleIdEqualTo(roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuDAO.selectByExample(roleMenuExample);
        // 菜单ID
        List<Long> sysRoleMenuIdList = Lists.transform(sysRoleMenuList, (SysRoleMenu sysRoleItem) -> sysRoleItem.getMenuId());
        // 一级菜单
        List<SysMenu> firstLevelMenu = sysMenuList.stream()
                .filter((SysMenu sysMenuItem) -> sysMenuItem.getParentId() == 0)
                .collect(Collectors.toList());
        return getMenuList(firstLevelMenu, sysMenuList, sysRoleMenuIdList);
    }

    private List<SysRoleMenuItemResponseDTO> getMenuList(List<SysMenu> firstLevelMenu, List<SysMenu> sysMenuList, List<Long> sysRoleMenuIdList) {
        List<SysRoleMenuItemResponseDTO> menuResponseList = new ArrayList<>();
        for (SysMenu sysMenuItem : firstLevelMenu) {
            SysRoleMenuItemResponseDTO sysRoleMenuItemResponseDTO = new SysRoleMenuItemResponseDTO();
            BeanUtils.copyProperties(sysMenuItem, sysRoleMenuItemResponseDTO);
            sysRoleMenuItemResponseDTO.setAuthorized(sysRoleMenuIdList.contains(sysMenuItem.getMenuId()));
            // 子菜单
            List<SysMenu> secondLevelMenu = sysMenuList.stream()
                    .filter((SysMenu secondSysMenuItem) -> secondSysMenuItem.getParentId().equals(sysMenuItem.getMenuId()))
                    .collect(Collectors.toList());
            sysRoleMenuItemResponseDTO.setChildren(getMenuList(secondLevelMenu, sysMenuList, sysRoleMenuIdList));
            menuResponseList.add(sysRoleMenuItemResponseDTO);
        }
        return menuResponseList;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateRoleMenuList(SysRoleMenuRequestDTO sysRoleMenuRequestDTO, Long updateRoleId) {
        ValidatorUtils.validateEntity(sysRoleMenuRequestDTO, UpdateGroup.class);
        SysRole role = sysRoleDAO.selectByPrimaryKey(sysRoleMenuRequestDTO.getRoleId());
        if (role == null) {
            throw new CustomException(ErrorCode.SYS_ROLE_NOT_FOUND.getCode(), ErrorCode.SYS_ROLE_NOT_FOUND.getMessage());
        }
        Long[] menuIds = sysRoleMenuRequestDTO.getMenuIds();
        SysMenuExample menuExample = new SysMenuExample();
        List<SysMenu> sysMenuList = sysMenuDAO.selectByExample(menuExample);
        Set<Long> menuIdList = new HashSet<>();
        for (Long menuId : menuIds ) {
            menuIdList = getAllMenuIds(menuId, sysMenuList, menuIdList);
        }
        // 删除所有角色相关的权限
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andRoleIdEqualTo(role.getRoleId());
        sysRoleMenuDAO.deleteByExample(roleMenuExample);
        for (Long menuId : menuIdList ) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(role.getRoleId());
            sysRoleMenu.setCreateUserId(updateRoleId);
            sysRoleMenu.setCreateTime(new Date());
            sysRoleMenuDAO.insertSelective(sysRoleMenu);
        }
    }

    private Set<Long> getAllMenuIds (Long roleId, List<SysMenu> sysMenuList, Set<Long> menuIdList) {
        List<SysMenu> secondLevelMenu = sysMenuList.stream()
                .filter((SysMenu secondSysMenuItem) -> secondSysMenuItem.getMenuId().equals(roleId))
                .collect(Collectors.toList());
        if (secondLevelMenu != null && secondLevelMenu.size() > 0) {
            menuIdList.add(roleId);
            if (secondLevelMenu.get(0).getParentId() != 0) {
                menuIdList.add(secondLevelMenu.get(0).getParentId());
                getAllMenuIds(secondLevelMenu.get(0).getParentId(), sysMenuList, menuIdList);
            }
        }
        return menuIdList;
    }
}
