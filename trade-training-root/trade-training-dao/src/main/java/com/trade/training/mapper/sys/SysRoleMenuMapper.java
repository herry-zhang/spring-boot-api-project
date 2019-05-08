package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysRoleMenu;
import com.trade.training.model.entity.sys.SysRoleMenuExample;
import org.springframework.stereotype.Repository;

/**
 * SysRoleMenuMapper继承基类
 *
 * @author herry-zhang
 */
@Repository
public interface SysRoleMenuMapper extends MyBatisBaseDao<SysRoleMenu, Long, SysRoleMenuExample> {
}