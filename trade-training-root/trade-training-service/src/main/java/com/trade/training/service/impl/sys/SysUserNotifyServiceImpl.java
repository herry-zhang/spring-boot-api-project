package com.trade.training.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.trade.training.common.exception.CustomException;
import com.trade.training.common.validator.ValidatorUtils;
import com.trade.training.config.ErrorCode;
import com.trade.training.config.YesOrNoCode;
import com.trade.training.mapper.sys.*;
import com.trade.training.mapper.sys.SysUserLogMapper;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.BasePageResponsePaginationDTO;
import com.trade.training.model.entity.sys.SysUser;
import com.trade.training.model.entity.sys.SysUserNotify;
import com.trade.training.model.entity.sys.SysUserNotifyExample;
import com.trade.training.service.sys.SysUserNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author herry-zhang
 * @date 2018-8-25 13:48:26
 */
@Service
public class SysUserNotifyServiceImpl implements SysUserNotifyService {

    @Autowired
    SysUserLogMapper sysUserLogDAO;
    @Autowired
    SysUserMapper sysUserDAO;
    @Autowired
    SysUserNotifyMapper sysUserNotifyDAO;

    @Override
    public void changeSysUserNotifyStatus(Long notifyId, Long userId) {
        SysUserNotify sysUserNotify = sysUserNotifyDAO.selectByPrimaryKey(notifyId);
        if (sysUserNotify == null) {
            throw new CustomException(ErrorCode.SYS_USER_NOTIFY_NOT_EXISTED.getCode(), ErrorCode.SYS_USER_NOTIFY_NOT_EXISTED.getMessage());
        }
        if (!sysUserNotify.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.SYS_USER_NOTIFY_NOT_EXISTED.getCode(), ErrorCode.SYS_USER_NOTIFY_NOT_EXISTED.getMessage());
        }
        sysUserNotify.setStatus((byte) YesOrNoCode.YES.getCode());
        sysUserNotifyDAO.updateByPrimaryKeySelective(sysUserNotify);
    }

    @Override
    public void clearSysUserNotify(Long userId) {
        SysUserNotify sysUserNotify = new SysUserNotify();
        sysUserNotify.setStatus((byte) YesOrNoCode.YES.getCode());
        SysUserNotifyExample sysUserNotifyExample = new SysUserNotifyExample();
        SysUserNotifyExample.Criteria sysUserNotifyCriteria = sysUserNotifyExample.createCriteria();
        sysUserNotifyCriteria.andUserIdEqualTo(userId);
        sysUserNotifyCriteria.andStatusEqualTo((byte) YesOrNoCode.NO.getCode());
        sysUserNotifyDAO.updateByExampleSelective(sysUserNotify, sysUserNotifyExample);
    }

    @Override
    public BasePageResponseListDTO<SysUserNotify> getSysUserNotifyList(BasePageRequestDTO<Long> basePageRequestDTO) {
        ValidatorUtils.validateEntity(basePageRequestDTO);
        SysUser sysUser = sysUserDAO.selectByPrimaryKey(basePageRequestDTO.getSearch());
        if (sysUser == null) {
            throw new CustomException(ErrorCode.SYS_USER_NOT_FOUND.getCode(), ErrorCode.SYS_USER_NOT_FOUND.getMessage());
        }
        BasePageResponseListDTO<SysUserNotify> sysUserNotifyResponseList = new BasePageResponseListDTO<>();
        SysUserNotifyExample sysUserNotifyExample = new SysUserNotifyExample();
        sysUserNotifyExample.setOrderByClause("notify_id desc");
        SysUserNotifyExample.Criteria sysUserNotifyCriteria = sysUserNotifyExample.createCriteria();
        sysUserNotifyCriteria.andUserIdEqualTo(sysUser.getUserId());
        sysUserNotifyCriteria.andStatusEqualTo((byte) YesOrNoCode.NO.getCode());
        PageHelper.startPage(basePageRequestDTO.getPageNum(), basePageRequestDTO.getPageSize());
        List<SysUserNotify> sysUserNotifyList = sysUserNotifyDAO.selectByExample(sysUserNotifyExample);
        BasePageResponsePaginationDTO pagination = new BasePageResponsePaginationDTO();
        pagination.setCurrent(basePageRequestDTO.getPageNum());
        pagination.setPageSize(basePageRequestDTO.getPageSize());
        pagination.setTotal(PageHelper.count(() -> sysUserNotifyDAO.selectByExample(sysUserNotifyExample)));
        sysUserNotifyResponseList.setPagination(pagination);
        sysUserNotifyResponseList.setList(sysUserNotifyList);
        return sysUserNotifyResponseList;
    }
}
