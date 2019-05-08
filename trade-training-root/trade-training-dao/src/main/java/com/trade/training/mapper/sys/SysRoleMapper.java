package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysRole;
import com.trade.training.model.entity.sys.SysRoleExample;
import org.springframework.stereotype.Repository;

/**
 * SysRoleMapper继承基类
 *
 * @author herry-zhang
 */
@Repository
public interface SysRoleMapper extends MyBatisBaseDao<SysRole, Long, SysRoleExample> {
}