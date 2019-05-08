package com.trade.training.service.sys;

import com.trade.training.model.entity.sys.SysUser;

import java.util.Set;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
public interface SysShiroService {

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUser selectByPrimaryKey(Long userId);

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
}
