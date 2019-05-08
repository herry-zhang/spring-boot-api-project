package com.trade.training.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页参数基础类
 *
 * @author herryzhang
 * @date 2018-7-24 14:59:54
 */
@Data
public class BasePageRequestDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 搜索条件
     */
    private T search;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不正确")
    private Integer pageNum;

    /**
     * 每页显示数量
     */
    @NotNull(message = "每页显示数量不能为空")
    @Min(value = 1, message = "每页显示数量不正确")
    private Integer pageSize;

    /**
     * 排序
     */
    private String orderBy;
}
