package com.trade.training.service.sys;

import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.entity.sys.SysUserNotify;

/**
 * @author herry-zhang
 * @date 2018-8-25 13:43:41
 */
public interface SysUserNotifyService {

    /**
     * 修改通知信息
     *
     * @param notifyId 通知
     * @param userId   用户ID
     */
    void changeSysUserNotifyStatus(Long notifyId, Long userId);

    /**
     * 清空通知信息
     *
     * @param userId   用户ID
     */
    void clearSysUserNotify(Long userId);

    /**
     * 搜索通知列表
     *
     * @param basePageRequestDTO 搜索条件
     * @return 通知列表
     */
    BasePageResponseListDTO<SysUserNotify> getSysUserNotifyList(BasePageRequestDTO<Long> basePageRequestDTO);
}
