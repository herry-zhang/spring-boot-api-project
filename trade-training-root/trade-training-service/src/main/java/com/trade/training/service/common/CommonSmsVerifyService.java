package com.trade.training.service.common;


import com.trade.training.model.dto.request.common.CommonSmsVerifyRequestDTO;

/**
 * @author herry-zhang
 * @date 2018-9-2 17:02:05
 */
public interface CommonSmsVerifyService {

    /**
     * 添加验证码
     *
     * @param commonSmsVerifyRequestDTO
     * @return
     */
    void addSmsVerify(CommonSmsVerifyRequestDTO commonSmsVerifyRequestDTO);

    /**
     * 发送验证码
     * @param commonSmsVerifyRequestDTO
     */
    void sendSms(CommonSmsVerifyRequestDTO commonSmsVerifyRequestDTO);

}
