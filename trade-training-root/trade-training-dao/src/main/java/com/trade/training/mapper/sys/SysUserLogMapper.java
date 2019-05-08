package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysUserLog;
import com.trade.training.model.entity.sys.SysUserLogExample;
import org.springframework.stereotype.Repository;

/**
 * SysUserLogMapper继承基类
 *
 * @author herry-zhang
 */
@Repository
public interface SysUserLogMapper extends MyBatisBaseDao<SysUserLog, Long, SysUserLogExample> {
}