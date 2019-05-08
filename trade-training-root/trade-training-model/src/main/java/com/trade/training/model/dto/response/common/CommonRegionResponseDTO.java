package com.trade.training.model.dto.response.common;

import lombok.Data;

import java.util.List;

/**
 * 省市区信息
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class CommonRegionResponseDTO {

    /**
     * 主键
     */
    Integer value;

    /**
     * 名称
     */
    String label;

    /**
     * 子节点
     */
    List<CommonRegionResponseDTO> children;
}
