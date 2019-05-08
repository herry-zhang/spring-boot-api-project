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
import com.trade.training.config.SysUserLogTypeCode;
import com.trade.training.config.SysUserStatusCode;
import com.trade.training.config.YesOrNoCode;
import com.trade.training.mapper.sys.*;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.LoginRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserDeleteRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserSearchRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.BasePageResponsePaginationDTO;
import com.trade.training.model.dto.response.sys.LoginSysUserResponseDTO;
import com.trade.training.model.dto.response.sys.SysUserProfileResponseDTO;
import com.trade.training.model.dto.response.sys.SysUserResponseDTO;
import com.trade.training.model.dto.response.sys.SysUserSearchResponseDTO;
import com.trade.training.model.entity.sys.*;
import com.trade.training.service.sys.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.trade.training.config.ConfigConst.SYS_ADMIN_ROLE_NAME;
import static com.trade.training.config.ConfigConst.SYS_DEFAULT_AVATAR_URL;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserDAO;
    @Autowired
    private SysUserLogMapper sysUserLogDAO;
    @Autowired
    private SysUserRoleMapper sysUserRoleDAO;
    @Autowired
    private SysRoleMapper sysRoleDAO;
    @Autowired
    private SysRoleEnabledRoleMapper sysRoleEnabledRoleDAO;
    @Autowired
    private SysUserNotifyMapper sysUserNotifyDAO;

    @Override
    public SysUserResponseDTO selectByPrimaryKey(Long userId) {
        SysUser user = sysUserDAO.selectByPrimaryKey(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.SYS_USER_NOT_FOUND.getCode(), ErrorCode.SYS_USER_NOT_FOUND.getMessage());
        }
        SysUserResponseDTO sysUserResponseDTO = new SysUserResponseDTO();
        BeanUtils.copyProperties(user, sysUserResponseDTO);
        // 角色信息
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleDAO.selectByExample(example);
        // 角色ID
        List<Long> roleResponseList = Lists.transform(sysUserRoleList, (SysUserRole sysUserRoleItem) -> sysUserRoleItem.getRoleId());
        sysUserResponseDTO.setRoleList(roleResponseList.toArray(new Long[roleResponseList.size()]));
        return sysUserResponseDTO;
    }

    @Override
    public LoginSysUserResponseDTO getLoginUser(Long userId) {
        SysUser user = sysUserDAO.selectByPrimaryKey(userId);
        LoginSysUserResponseDTO loginSysUserResponseDTO = new LoginSysUserResponseDTO();
        BeanUtils.copyProperties(user, loginSysUserResponseDTO);
        if (StringUtils.isBlank(loginSysUserResponseDTO.getNickName())) {
            loginSysUserResponseDTO.setNickName(user.getUserName());
        }
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleDAO.selectByExample(example);
        // 角色ID
        List<Long> roleResponseList = Lists.transform(sysUserRoleList, (SysUserRole sysUserRoleItem) -> sysUserRoleItem.getRoleId());
        // 角色详情列表
        List<SysRole> sysRoleResponseList = new ArrayList<>();
        if (roleResponseList.size() > 0) {
            SysRoleExample roleExample = new SysRoleExample();
            roleExample.createCriteria().andRoleIdIn(roleResponseList);
            sysRoleResponseList = sysRoleDAO.selectByExample(roleExample);
        }
        // 角色名称
        List<String> sysRoleNameResponseList = Lists.transform(sysRoleResponseList, (SysRole sysRoleItem) -> sysRoleItem.getRoleName());
        loginSysUserResponseDTO.setRoleList(sysRoleNameResponseList.toArray(new String[sysRoleNameResponseList.size()]));
        SysUserNotifyExample sysUserNotifyExample = new SysUserNotifyExample();
        SysUserNotifyExample.Criteria sysUserNotifyCriteria = sysUserNotifyExample.createCriteria();
        sysUserNotifyCriteria.andUserIdEqualTo(userId);
        sysUserNotifyCriteria.andStatusEqualTo((byte) YesOrNoCode.NO.getCode());
        loginSysUserResponseDTO.setNotifyCount((int) sysUserNotifyDAO.countByExample(sysUserNotifyExample));
        return loginSysUserResponseDTO;
    }

    @Override
    public SysUserProfileResponseDTO getSysUserProfile(Long userId) {
        SysUser user = sysUserDAO.selectByPrimaryKey(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.SYS_USER_NOT_FOUND.getCode(), ErrorCode.SYS_USER_NOT_FOUND.getMessage());
        }
        SysUserProfileResponseDTO sysUserProfileResponseDTO = new SysUserProfileResponseDTO();
        BeanUtils.copyProperties(user, sysUserProfileResponseDTO);
        SysUser director = sysUserDAO.selectByPrimaryKey(user.getDirectorUserId());
        if (director != null) {
            sysUserProfileResponseDTO.setDirector(StringUtils.isBlank(director.getNickName()) ? director.getUserName(): director.getNickName());
        }
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleDAO.selectByExample(example);
        // 角色ID
        List<Long> roleResponseList = Lists.transform(sysUserRoleList, (SysUserRole sysUserRoleItem) -> sysUserRoleItem.getRoleId());
        // 角色详情列表
        List<SysRole> sysRoleResponseList = new ArrayList<>();
        if (roleResponseList.size() > 0) {
            SysRoleExample roleExample = new SysRoleExample();
            roleExample.createCriteria().andRoleIdIn(roleResponseList);
            sysRoleResponseList = sysRoleDAO.selectByExample(roleExample);
        }
        // 角色名称
        List<String> sysRoleNameResponseList = Lists.transform(sysRoleResponseList, (SysRole sysRoleItem) -> sysRoleItem.getRemark());
        sysUserProfileResponseDTO.setRoleList(sysRoleNameResponseList.toArray(new String[sysRoleNameResponseList.size()]));
        return sysUserProfileResponseDTO;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SysUser login(LoginRequestDTO loginRequestDTO, Byte sourceCode, String ip) {
        ValidatorUtils.validateEntity(loginRequestDTO);
        SysUser user = this.queryByUserName(loginRequestDTO.getUserName());
        if (user == null || user.getStatus() == SysUserStatusCode.DELETE.getCode()) {
            throw new CustomException(ErrorCode.SYS_USER_NOT_FOUND.getCode(), ErrorCode.SYS_USER_NOT_FOUND.getMessage());
        } else if (!user.getPassword().equals(new Sha256Hash(loginRequestDTO.getPassword(), user.getSalt()).toHex())) {
            throw new CustomException(ErrorCode.SYS_USER_PASSWORD_ERROR.getCode(), ErrorCode.SYS_USER_PASSWORD_ERROR.getMessage());
        } else if (user.getStatus() == SysUserStatusCode.LOCKED.getCode()) {
            throw new CustomException(ErrorCode.SYS_USER_LOCKED.getCode(), ErrorCode.SYS_USER_LOCKED.getMessage());
        }
        user.setLastLoginTime(new Date());
        sysUserDAO.updateByPrimaryKey(user);
        // 操作日志
        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setUserId(user.getUserId());
        sysUserLog.setTypeId(SysUserLogTypeCode.LOGIN.getCode());
        sysUserLog.setSourceId(sourceCode);
        sysUserLog.setIp(ip);
        sysUserLog.setCreateTime(new Date());
        sysUserLogDAO.insert(sysUserLog);
        return user;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addUser(SysUserRequestDTO sysUserRequestDTO, Long createUserId) {
        ValidatorUtils.validateEntity(sysUserRequestDTO, AddGroup.class);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRequestDTO, sysUser);
        if (this.queryByUserName(sysUser.getUserName()) != null) {
            throw new CustomException(ErrorCode.SYS_USER_NAME_EXISTED.getCode(), ErrorCode.SYS_USER_NAME_EXISTED.getMessage());
        }
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserIdEqualTo(createUserId);
        List<SysUserRole> sysUserRoleList = sysUserRoleDAO.selectByExample(sysUserRoleExample);
        if (sysUserRoleList.size() == 0) {
            throw new CustomException(ErrorCode.SYS_USER_ADD_UNAUTHORIZED.getCode(), ErrorCode.SYS_USER_ADD_UNAUTHORIZED.getMessage());
        }
        List<Long> sysUserRoleIdList = Lists.transform(sysUserRoleList, (SysUserRole sysUserRole) -> sysUserRole.getRoleId());
        SysRoleEnabledRoleExample example = new SysRoleEnabledRoleExample();
        example.createCriteria().andRoleIdIn(sysUserRoleIdList);
        List<SysRoleEnabledRole> sysRoleEnabledRoleList = sysRoleEnabledRoleDAO.selectByExample(example);
        if (sysRoleEnabledRoleList.size() == 0) {
            throw new CustomException(ErrorCode.SYS_USER_ADD_UNAUTHORIZED.getCode(), ErrorCode.SYS_USER_ADD_UNAUTHORIZED.getMessage());
        }
        // 添加用户
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(sysUser.getPassword(), salt).toHex());
        sysUser.setSalt(salt);
        sysUser.setStatus((byte) SysUserStatusCode.NORMAL.getCode());
        if (StringUtils.isBlank(sysUserRequestDTO.getAvatarUrl())) {
            sysUser.setAvatarUrl(SYS_DEFAULT_AVATAR_URL);
        }
        // 默认创建人为该用户的主管
        sysUser.setDirectorUserId(createUserId);
        sysUser.setCreateUserId(createUserId);
        sysUser.setCreateTime(new Date());
        sysUserDAO.insertSelective(sysUser);
        // 添加角色 - 原本设计的多角色，这里限定了一个用户只能是指定的角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getUserId());
        sysUserRole.setRoleId(sysRoleEnabledRoleList.get(0).getEnabledRoleId());
        sysUserRole.setCreateUserId(createUserId);
        sysUserRole.setCreateTime(new Date());
        sysUserRoleDAO.insertSelective(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateUser(SysUserRequestDTO sysUserRequestDTO, Long updateUserId) {
        ValidatorUtils.validateEntity(sysUserRequestDTO, UpdateGroup.class);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRequestDTO, sysUser);
        SysUser user = sysUserDAO.selectByPrimaryKey(sysUser.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.SYS_USER_NOT_FOUND.getCode(), ErrorCode.SYS_USER_NOT_FOUND.getMessage());
        } else if (!StringUtils.isBlank(sysUser.getUserName()) && !sysUser.getUserName().equals(user.getUserName())  && this.queryByUserName(sysUser.getUserName()) != null) {
            throw new CustomException(ErrorCode.SYS_USER_NAME_EXISTED.getCode(), ErrorCode.SYS_USER_NAME_EXISTED.getMessage());
        }
        // 修改用户
        if (StringUtils.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(null);
        } else {
            sysUser.setPassword(new Sha256Hash(sysUser.getPassword(), user.getSalt()).toHex());
        }
        sysUser.setUpdateUserId(updateUserId);
        sysUser.setUpdateTime(new Date());
        sysUserDAO.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUser(SysUserDeleteRequestDTO sysUserDeleteRequestDTO, Long updateUserId) {
        Long[] userIds = sysUserDeleteRequestDTO.getUserIds();
        if (ArrayUtils.contains(userIds, 1L)) {
            throw new CustomException(ErrorCode.SYS_USER_DELETE_ERROR.getCode(), ErrorCode.SYS_USER_DELETE_ERROR.getMessage() + "，系统管理员不能删除");
        } else if (ArrayUtils.contains(userIds, updateUserId)) {
            throw new CustomException(ErrorCode.SYS_USER_DELETE_ERROR.getCode(), ErrorCode.SYS_USER_DELETE_ERROR.getMessage() + "，当前用户不能删除");
        }
        // 修改用户删除标识
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        SysUser sysUser = new SysUser();
        sysUser.setStatus((byte) SysUserStatusCode.DELETE.getCode());
        sysUser.setUpdateUserId(updateUserId);
        sysUser.setUpdateTime(new Date());
        sysUserDAO.updateByExampleSelective(sysUser, example);
    }

    @Override
    public SysUser queryByUserName(String userName) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUserNameEqualTo(userName);
        List<SysUser> list = sysUserDAO.selectByExample(sysUserExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<String> permsList;
        permsList = sysUserDAO.queryAllPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public BasePageResponseListDTO<SysUserSearchResponseDTO> getUserList(BasePageRequestDTO<SysUserSearchRequestDTO> basePageRequestDTO, Long currentUserId) {
        ValidatorUtils.validateEntity(basePageRequestDTO);
        BasePageResponseListDTO<SysUserSearchResponseDTO> userResponse = new BasePageResponseListDTO<>();
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.setOrderByClause("user_id desc");
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        // 判断用户是否为管理员
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserIdEqualTo(currentUserId);
        List<SysUserRole> userRoleList = sysUserRoleDAO.selectByExample(sysUserRoleExample);
        List<Long> roleIds = Lists.transform(userRoleList, (SysUserRole sysUserRoleItem) -> sysUserRoleItem.getRoleId());
        SysRoleExample roleExample = new SysRoleExample();
        roleExample.createCriteria().andRoleIdIn(roleIds);
        List<SysRole> sysRoleList = sysRoleDAO.selectByExample(roleExample);
        boolean isAdmin = false;
        for (SysRole sysRoleItem : sysRoleList) {
            if (sysRoleItem.getRoleName().equals(SYS_ADMIN_ROLE_NAME)) {
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            criteria.andDdirectorUserIdEqualTo(currentUserId);
        }
        // 搜索条件
        SysUserSearchRequestDTO sysUserSearchRequestDTO = basePageRequestDTO.getSearch();
        if (sysUserSearchRequestDTO != null) {
            if (sysUserSearchRequestDTO.getUserId() != null) {
                criteria.andUserIdEqualTo(sysUserSearchRequestDTO.getUserId());
            }
            if (StringUtils.isNotBlank(sysUserSearchRequestDTO.getUserName())) {
                criteria.andUserNameLike(SqlUtils.concatLike(sysUserSearchRequestDTO.getUserName(), SqlLikeEnum.DEFAULT));
            }
            if (StringUtils.isNotBlank(sysUserSearchRequestDTO.getNickName())) {
                criteria.andNickNameLike(SqlUtils.concatLike(sysUserSearchRequestDTO.getNickName(), SqlLikeEnum.DEFAULT));
            }
            if (StringUtils.isNotBlank(sysUserSearchRequestDTO.getEmail())) {
                criteria.andEmailLike(SqlUtils.concatLike(sysUserSearchRequestDTO.getEmail(), SqlLikeEnum.DEFAULT));
            }
            if (StringUtils.isNotBlank(sysUserSearchRequestDTO.getMobile())) {
                criteria.andMobileLike(SqlUtils.concatLike(sysUserSearchRequestDTO.getMobile(), SqlLikeEnum.DEFAULT));
            }
            if (sysUserSearchRequestDTO.getStatus() != null) {
                criteria.andStatusEqualTo(sysUserSearchRequestDTO.getStatus());
            }
            if (sysUserSearchRequestDTO.getStartTime() != null && sysUserSearchRequestDTO.getEndTime() != null) {
                criteria.andLastLoginTimeBetween(sysUserSearchRequestDTO.getStartTime(), sysUserSearchRequestDTO.getEndTime());
            } else if (sysUserSearchRequestDTO.getStartTime() != null) {
                criteria.andLastLoginTimeGreaterThanOrEqualTo(sysUserSearchRequestDTO.getStartTime());
            } else if (sysUserSearchRequestDTO.getEndTime() != null) {
                criteria.andLastLoginTimeLessThanOrEqualTo(sysUserSearchRequestDTO.getEndTime());
            }
        }
        criteria.andStatusNotEqualTo((byte) SysUserStatusCode.DELETE.getCode());
        PageHelper.startPage(basePageRequestDTO.getPageNum(), basePageRequestDTO.getPageSize());
        List<SysUser> userList = sysUserDAO.selectByExample(sysUserExample);
        List<SysUserSearchResponseDTO> userResponseList = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            // 搜索的全部用户角色信息
            SysUserRoleExample example = new SysUserRoleExample();
            example.createCriteria().andUserIdIn(Lists.transform(userList, (SysUser sysUserItem) -> sysUserItem.getUserId()));
            List<SysUserRole> sysUserRoleList = sysUserRoleDAO.selectByExample(example);
            // 角色ID
            List<Long> roleResponseList = Lists.transform(sysUserRoleList, (SysUserRole sysUserRoleItem) -> sysUserRoleItem.getRoleId());
            // 角色详情列表
            SysRoleExample roleListExample = new SysRoleExample();
            roleExample.createCriteria().andRoleIdIn(roleResponseList);
            List<SysRole> sysRoleResponseList = sysRoleDAO.selectByExample(roleListExample);
            // 赋值传输用户信息
            userResponseList = Lists.transform(userList, (SysUser sysUserItem) -> {
                SysUserSearchResponseDTO sysUserSearchResponseDTO = new SysUserSearchResponseDTO();
                BeanUtils.copyProperties(sysUserItem, sysUserSearchResponseDTO);
                List<SysUserRole> userRoleItemList = sysUserRoleList.stream()
                        .filter((SysUserRole sysUserRole) -> sysUserRole.getUserId().equals(sysUserSearchResponseDTO.getUserId()))
                        .collect(Collectors.toList());
                List<SysRole> roleItemList = sysRoleResponseList.stream()
                        .filter((SysRole sysRole) -> {
                            boolean isUserRole = false;
                            for (SysUserRole sysUserRoleItem : userRoleItemList) {
                                if (sysUserRoleItem.getRoleId().equals(sysRole.getRoleId())) {
                                    isUserRole = true;
                                    break;
                                }
                            }
                            if (isUserRole) {
                                return true;
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
                List<String> roleNameList = Lists.transform(roleItemList, (SysRole sysRoleItem) -> sysRoleItem.getRemark());
                sysUserSearchResponseDTO.setRoleList(roleNameList);
                return sysUserSearchResponseDTO;
            });
        }
        BasePageResponsePaginationDTO pagination = new BasePageResponsePaginationDTO();
        pagination.setCurrent(basePageRequestDTO.getPageNum());
        pagination.setPageSize(basePageRequestDTO.getPageSize());
        pagination.setTotal(PageHelper.count(() -> sysUserDAO.selectByExample(sysUserExample)));
        userResponse.setPagination(pagination);
        userResponse.setList(userResponseList);
        return userResponse;
    }

    @Override
    public List<SysUserSearchResponseDTO> getDepartmentUserList(Long currentUserId) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.setOrderByClause("user_id desc");
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        criteria.andDdirectorUserIdEqualTo(currentUserId);
        criteria.andStatusEqualTo((byte) SysUserStatusCode.NORMAL.getCode());
        List<SysUser> userList = sysUserDAO.selectByExample(sysUserExample);
        List<SysUserSearchResponseDTO> userResponseList = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            // 赋值传输用户信息
            userResponseList = Lists.transform(userList, (SysUser sysUserItem) -> {
                SysUserSearchResponseDTO sysUserSearchResponseDTO = new SysUserSearchResponseDTO();
                BeanUtils.copyProperties(sysUserItem, sysUserSearchResponseDTO);
                return sysUserSearchResponseDTO;
            });
        }
        return userResponseList;
    }
}
