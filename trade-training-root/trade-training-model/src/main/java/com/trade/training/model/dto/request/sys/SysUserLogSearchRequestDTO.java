package com.trade.training.model.dto.request.sys;

import lombok.Data;

/**
 * 获取用户日志
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserLogSearchRequestDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 类型ID
     */
    private Integer typeId;
}
