package com.trade.training.model.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数基础类
 *
 * @author herryzhang
 * @date 2018-7-24 14:59:54
 */
@Data
public class BasePageResponsePaginationDTO implements Serializable {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页显示数量
     */
    private Integer pageSize;
}
