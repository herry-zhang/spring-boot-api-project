package com.trade.training.admin.controller.sys;

import com.trade.training.admin.shiro.ShiroUtils;
import com.trade.training.config.SysUserStatusCode;
import com.trade.training.model.dto.request.sys.SysUserDeleteRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserLogSearchRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserRequestDTO;
import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserSearchRequestDTO;
import com.trade.training.service.sys.SysUserLogService;
import com.trade.training.service.sys.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 用户相关
 *
 * @author herry-zhang
 * @date 2018-7-24 17:29:15
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DefaultWebSessionManager sessionManager;
    @Autowired
    private SysUserLogService sysUserLogService;

    /**
     * 获取用户信息
     */
    @GetMapping("/user/{userId}")
    @RequiresPermissions("sys:user:info")
    public BaseResponseDTO getUser(@PathVariable("userId") Long userId) {
        return BaseResponseDTO.success(sysUserService.selectByPrimaryKey(userId));
    }

    /**
     * 添加系统用户
     */
    @PostMapping("/user/add")
    @RequiresPermissions("sys:user:add")
    public BaseResponseDTO addUser(@RequestBody SysUserRequestDTO sysUserRequestDTO) {
        sysUserService.addUser(sysUserRequestDTO, ShiroUtils.getUserID());
        return BaseResponseDTO.success("用户添加成功");
    }

    /**
     * 修改系统用户信息
     */
    @PostMapping("/user/update")
    @RequiresPermissions("sys:user:update")
    public BaseResponseDTO updateUser(@RequestBody SysUserRequestDTO sysUserRequestDTO) {
        sysUserService.updateUser(sysUserRequestDTO, ShiroUtils.getUserID());
        if (sysUserRequestDTO.getStatus() != null && sysUserRequestDTO.getStatus() == SysUserStatusCode.LOCKED.getCode()) {
            Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
            for (Session session : sessions) {
                Object sessionUserId = session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
                if (sessionUserId != null && sysUserRequestDTO.getUserId().equals(Long.parseLong(sessionUserId.toString()))) {
                    session.stop();
                }
            }
        }
        return BaseResponseDTO.success("用户信息修改成功");
    }

    /**
     * 删除系统用户
     */
    @PostMapping("/user/delete")
    @RequiresPermissions("sys:user:delete")
    public BaseResponseDTO deleteUser(@RequestBody SysUserDeleteRequestDTO sysUserDeleteRequestDTO) {
        sysUserService.deleteUser(sysUserDeleteRequestDTO, ShiroUtils.getUserID());
        return BaseResponseDTO.success("删除用户成功");
    }

    /**
     * 获取用户列表
     */
    @PostMapping("/user/list")
    @RequiresPermissions("sys:user:list")
    public BaseResponseDTO getUserList(@RequestBody BasePageRequestDTO<SysUserSearchRequestDTO> basePageRequestDTO) {
        return BaseResponseDTO.success(sysUserService.getUserList(basePageRequestDTO, ShiroUtils.getUserID()));
    }

    /**
     * 获取用户操作日志
     */
    @PostMapping("/user/log")
    public BaseResponseDTO getUserLog(@RequestBody BasePageRequestDTO<SysUserLogSearchRequestDTO> basePageRequestDTO) {
        return BaseResponseDTO.success(sysUserLogService.getUserLogList(basePageRequestDTO));
    }

    /**
     * 获取部门用户列表
     */
    @GetMapping("/department/user/list")
    @RequiresPermissions("sys:user:list")
    public BaseResponseDTO getDepartmentUserList() {
        return BaseResponseDTO.success(sysUserService.getDepartmentUserList(ShiroUtils.getUserID()));
    }
}
