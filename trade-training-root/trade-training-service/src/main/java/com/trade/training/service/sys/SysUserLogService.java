package com.trade.training.service.sys;

import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserLogSearchRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.sys.SysUserLogResponseDTO;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
public interface SysUserLogService {

    /**
     * 搜索用户日志列表
     *
     * @param basePageRequestDTO 搜索条件
     * @return 用户日志列表
     */
    BasePageResponseListDTO<SysUserLogResponseDTO> getUserLogList(BasePageRequestDTO<SysUserLogSearchRequestDTO> basePageRequestDTO);
}
