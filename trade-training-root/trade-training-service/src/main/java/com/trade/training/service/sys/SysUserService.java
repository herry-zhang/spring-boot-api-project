package com.trade.training.service.sys;

import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.LoginRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserDeleteRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserSearchRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.sys.SysUserProfileResponseDTO;
import com.trade.training.model.dto.response.sys.LoginSysUserResponseDTO;
import com.trade.training.model.dto.response.sys.SysUserResponseDTO;
import com.trade.training.model.dto.response.sys.SysUserSearchResponseDTO;
import com.trade.training.model.entity.sys.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
public interface SysUserService {

    /**
     * 根据主键获取用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserResponseDTO selectByPrimaryKey(Long userId);

    /**
     * 根据主键获取用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    LoginSysUserResponseDTO getLoginUser(Long userId);

    /**
     * 获取用户的个人信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserProfileResponseDTO getSysUserProfile(Long userId);

    /**
     * 登录
     *
     * @param loginRequestDTO 登录信息
     * @param sourceCode      来源
     * @param ip              IP地址
     * @return
     */
    SysUser login(LoginRequestDTO loginRequestDTO, Byte sourceCode, String ip);

    /**
     * 添加用户
     *
     * @param sysUserRequestDTO 用户
     * @param createUserId      创建人ID
     */
    void addUser(SysUserRequestDTO sysUserRequestDTO, Long createUserId);

    /**
     * 修改系统用户信息
     *
     * @param sysUserRequestDTO 用户
     * @param updateUserId      修改人ID
     */
    void updateUser(SysUserRequestDTO sysUserRequestDTO, Long updateUserId);

    /**
     * 删除用户信息
     *
     * @param sysUserDeleteRequestDTO 用户ID
     * @param updateUserId            修改人ID
     */
    void deleteUser(SysUserDeleteRequestDTO sysUserDeleteRequestDTO, Long updateUserId);

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名称
     * @return 用户信息
     */
    SysUser queryByUserName(String userName);

    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 用户权限列表
     */
    Set<String> getUserPermissions(Long userId);

    /**
     * 搜索用户列表
     *
     * @param basePageRequestDTO 搜索条件
     * @param currentUserId      当前用户
     * @return 用户列表
     */
    BasePageResponseListDTO<SysUserSearchResponseDTO> getUserList(BasePageRequestDTO<SysUserSearchRequestDTO> basePageRequestDTO, Long currentUserId);
    /**
     * 搜索部门用户列表
     *
     * @param currentUserId      当前用户
     * @return 用户列表
     */
    List<SysUserSearchResponseDTO> getDepartmentUserList(Long currentUserId);
}
