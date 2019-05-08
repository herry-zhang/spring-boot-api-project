package com.trade.training.service.impl.sys;

import com.trade.training.mapper.sys.SysUserMapper;
import com.trade.training.model.entity.sys.SysUser;
import com.trade.training.model.entity.sys.SysUserExample;
import com.trade.training.service.sys.SysShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
@Service
public class SysSysShiroServiceImpl implements SysShiroService {

    @Autowired
    private SysUserMapper sysUserDAO;

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        return sysUserDAO.selectByPrimaryKey(userId);
    }

    @Override
    public SysUser queryByUserName(String userName) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUserNameEqualTo(userName);
        List<SysUser> list = sysUserDAO.selectByExample(sysUserExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;

    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<String> permsList;
        permsList = sysUserDAO.queryAllPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
