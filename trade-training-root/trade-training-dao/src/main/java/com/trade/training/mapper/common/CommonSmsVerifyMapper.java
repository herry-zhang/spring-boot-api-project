package com.trade.training.mapper.common;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.common.CommonSmsVerify;
import com.trade.training.model.entity.common.CommonSmsVerifyExample;
import org.springframework.stereotype.Repository;

/**
 * CommonSmsVerifyMapper继承基类
 */
@Repository
public interface CommonSmsVerifyMapper extends MyBatisBaseDao<CommonSmsVerify, Integer, CommonSmsVerifyExample> {
}