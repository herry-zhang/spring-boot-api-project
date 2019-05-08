package com.trade.training.service.common;

import com.trade.training.model.dto.response.common.CommonRegionResponseDTO;

import java.util.List;

/**
 * @author herry-zhang
 * @date 2018-9-2 17:02:05
 */
public interface CommonRegionService {

    /**
     * 获取地区信息
     *
     * @return
     */
    List<CommonRegionResponseDTO> getRegionList();
}
