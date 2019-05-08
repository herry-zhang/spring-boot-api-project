package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysUser;
import com.trade.training.model.entity.sys.SysUserExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SysUserMapper继承基类
 *
 * @author herry-zhang
 * @date 2018-7-24 15:27:34
 */
@Repository
public interface SysUserMapper extends MyBatisBaseDao<SysUser, Long, SysUserExample> {
    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return 用户权限列表
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId 用户ID
     * @return 用户菜单列表
     */
    List<Long> queryAllMenuId(Long userId);
}