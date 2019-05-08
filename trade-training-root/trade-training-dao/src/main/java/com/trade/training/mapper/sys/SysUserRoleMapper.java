package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysUserRole;
import com.trade.training.model.entity.sys.SysUserRoleExample;
import org.springframework.stereotype.Repository;

/**
 * SysUserRoleMapper继承基类
 *
 * @author herry-zhang
 */
@Repository
public interface SysUserRoleMapper extends MyBatisBaseDao<SysUserRole, Long, SysUserRoleExample> {
}