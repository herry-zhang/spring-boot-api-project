package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysRoleEnabledRole;
import com.trade.training.model.entity.sys.SysRoleEnabledRoleExample;
import org.springframework.stereotype.Repository;

/**
 * SysRoleEnabledRoleMapper继承基类
 */
@Repository
public interface SysRoleEnabledRoleMapper extends MyBatisBaseDao<SysRoleEnabledRole, Long, SysRoleEnabledRoleExample> {
}