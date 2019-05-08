package com.trade.training.mapper.common;

import com.trade.training.mapper.MyBatisBaseDao;
import com.trade.training.model.entity.common.CommonRegion;
import com.trade.training.model.entity.common.CommonRegionExample;
import org.springframework.stereotype.Repository;

/**
 * CommonRegionMapper继承基类
 */
@Repository
public interface CommonRegionMapper extends MyBatisBaseDao<CommonRegion, Integer, CommonRegionExample> {
}