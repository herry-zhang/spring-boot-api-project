package com.trade.training.admin.controller.sys;

import com.trade.training.admin.shiro.ShiroUtils;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.model.entity.sys.SysUserNotify;
import com.trade.training.service.sys.SysUserNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户通知相关
 *
 * @author herry-zhang
 * @date 2018-7-24 17:29:15
 */
@RestController
@RequestMapping("/sys")
public class SysUserNotifyController {

    @Autowired
    private SysUserNotifyService sysUserNotifyService;
    
    /**
     * 修改通知状态
     */
    @PostMapping("/notify/update")
    public BaseResponseDTO changeSysUserNotifyStatus(@RequestBody SysUserNotify sysUserNotify) {
        sysUserNotifyService.changeSysUserNotifyStatus(sysUserNotify.getNotifyId(), ShiroUtils.getUserID());
        return BaseResponseDTO.success("通知修改成功");
    }

    /**
     * 清空通知状态
     */
    @PostMapping("/notify/clear")
    public BaseResponseDTO clearSysUserNotify() {
        sysUserNotifyService.clearSysUserNotify(ShiroUtils.getUserID());
        return BaseResponseDTO.success("通知清空成功");
    }

    /**
     * 获取未读通知列表
     */
    @PostMapping("/notify/list")
    public BaseResponseDTO getSysUserNotifyList(@RequestBody BasePageRequestDTO<Long> basePageRequestDTO) {
        basePageRequestDTO.setSearch(ShiroUtils.getUserID());
        return BaseResponseDTO.success(sysUserNotifyService.getSysUserNotifyList(basePageRequestDTO));
    }
}
