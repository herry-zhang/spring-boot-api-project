package com.trade.training.model.dto.request.sys;

import com.trade.training.common.validator.group.AddGroup;
import com.trade.training.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * 系统用户信息参数
 *
 * @author herry-zhang
 * @date 2018-7-23 17:13:48
 */
@Data
public class SysUserDeleteRequestDTO {

    /**
     * 主键，自动递增
     */
    @NotNull(message = "用户ID不能为空", groups = {UpdateGroup.class})
    private Long[] userIds;
}
