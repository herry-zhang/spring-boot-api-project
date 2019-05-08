package com.trade.training.admin.shiro;

import com.trade.training.model.entity.sys.SysUser;
import com.trade.training.service.sys.SysShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysShiroService sysShiroService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = sysShiroService.selectByPrimaryKey((Long) SecurityUtils.getSubject().getPrincipal());
        Long userId = user.getUserId();
        //用户权限列表
        Set<String> permsSet = sysShiroService.getUserPermissions(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        //查询用户信息
        SysUser user = sysShiroService.queryByUserName(username);
        return new SimpleAuthenticationInfo(user.getUserId(), password, getName());
    }
}
