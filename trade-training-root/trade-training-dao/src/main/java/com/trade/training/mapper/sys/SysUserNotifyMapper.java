package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysUserNotify;
import com.trade.training.model.entity.sys.SysUserNotifyExample;
import org.springframework.stereotype.Repository;

/**
 * SysUserNotifyMapper继承基类
 */
@Repository
public interface SysUserNotifyMapper extends MyBatisBaseDao<SysUserNotify, Long, SysUserNotifyExample> {
}