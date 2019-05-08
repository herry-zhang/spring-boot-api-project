package com.trade.training.model.dto.response;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页参数基础类
 *
 * @author herryzhang
 * @date 2018-7-24 14:59:54
 */
@Data
public class BasePageResponseListDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果
     */
    private List<T> list;

    /**
     * 分页数据
     */
    private BasePageResponsePaginationDTO pagination;
}
