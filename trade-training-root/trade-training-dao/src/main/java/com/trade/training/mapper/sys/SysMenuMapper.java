package com.trade.training.mapper.sys;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.sys.SysMenu;
import com.trade.training.model.entity.sys.SysMenuExample;
import org.springframework.stereotype.Repository;

/**
 * SysMenuMapper继承基类
 * @author herry-zhang
 */
@Repository
public interface SysMenuMapper extends MyBatisBaseDao<SysMenu, Long, SysMenuExample> {
}