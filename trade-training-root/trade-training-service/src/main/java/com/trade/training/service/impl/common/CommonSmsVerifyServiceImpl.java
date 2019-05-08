package com.trade.training.service.impl.common;

import com.trade.training.common.exception.CustomException;
import com.trade.training.common.validator.ValidatorUtils;
import com.trade.training.config.AliSmsTypeCode;
import com.trade.training.config.ErrorCode;
import com.trade.training.mapper.common.CommonSmsVerifyMapper;
import com.trade.training.model.dto.request.common.CommonSmsVerifyRequestDTO;
import com.trade.training.model.entity.common.CommonSmsVerify;
import com.trade.training.service.common.AliSmsUtils;
import com.trade.training.service.common.CommonSmsVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author herry-zhang
 * @date 2018-9-2 17:05:49
 */
@Service
public class CommonSmsVerifyServiceImpl implements CommonSmsVerifyService {

    @Autowired
    private CommonSmsVerifyMapper commonSmsVerifyDAO;

    @Override
    public void addSmsVerify(CommonSmsVerifyRequestDTO commonSmsVerifyRequestDTO) {
        ValidatorUtils.validateEntity(commonSmsVerifyRequestDTO);
        Integer code = (int) ((Math.random() * 9 + 1) * 100000);
        Boolean isSend = AliSmsUtils.sentSms(AliSmsTypeCode.CLOUD_REGISTER.getCode(), commonSmsVerifyRequestDTO.getMobile(), code);
        if (!isSend) {
            throw new CustomException(ErrorCode.SUPPORT_SMS_VERIFY_SENT_FAILED.getCode(), ErrorCode.SUPPORT_SMS_VERIFY_SENT_FAILED.getMessage());
        }
        CommonSmsVerify commonSmsVerify = new CommonSmsVerify();
        commonSmsVerify.setMobile(commonSmsVerifyRequestDTO.getMobile());
        commonSmsVerify.setSmsType((short) AliSmsTypeCode.CLOUD_REGISTER.getCode());
        commonSmsVerify.setVerifyCode(code);
        Date now = new Date();
        commonSmsVerify.setExpireTime(new Date(now.getTime() + 900000));
        commonSmsVerify.setCreateTime(now);
        commonSmsVerifyDAO.insertSelective(commonSmsVerify);
    }

    @Override
    public void sendSms(CommonSmsVerifyRequestDTO commonSmsVerifyRequestDTO) {
        ValidatorUtils.validateEntity(commonSmsVerifyRequestDTO);
        Integer code = (int) ((Math.random() * 9 + 1) * 100000);
        Boolean isSend = AliSmsUtils.sentSms(AliSmsTypeCode.FORGET_PASSWORD.getCode(), commonSmsVerifyRequestDTO.getMobile(), code);
        if (!isSend) {
            throw new CustomException(ErrorCode.SUPPORT_SMS_VERIFY_SENT_FAILED.getCode(), ErrorCode.SUPPORT_SMS_VERIFY_SENT_FAILED.getMessage());
        }
        CommonSmsVerify commonSmsVerify = new CommonSmsVerify();
        commonSmsVerify.setMobile(commonSmsVerifyRequestDTO.getMobile());
        commonSmsVerify.setSmsType((short) AliSmsTypeCode.FORGET_PASSWORD.getCode());
        commonSmsVerify.setVerifyCode(code);
        Date now = new Date();
        commonSmsVerify.setExpireTime(new Date(now.getTime() + 900000));
        commonSmsVerify.setCreateTime(now);
        commonSmsVerifyDAO.insertSelective(commonSmsVerify);
    }
}
