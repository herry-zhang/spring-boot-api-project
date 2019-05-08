package com.trade.training.model.dto.response.sys;

import lombok.Data;

import java.util.Date;

/**
 * 用户日志
 *
 * @author herry-zhang
 * @date 2018-8-23 16:38:38
 */
@Data
public class SysUserLogResponseDTO {

    /**
     * 主键，自动递增
     */
    Long id;

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 操作类型
     */
    String type;

    /**
     * 操作时间
     */
    Date createTime;

    /**
     * 备注：如果有相关ID，说明操作
     */
    String remark;
}
