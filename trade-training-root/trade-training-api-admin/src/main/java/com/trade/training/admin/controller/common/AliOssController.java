package com.trade.training.admin.controller.common;

import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.service.common.AliOssUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author herry-zhang
 * @date 2018-7-16 19:20:38
 */
@RestController
@RequestMapping("/oss")
public class AliOssController {

    /**
     * 获取oss签名
     *
     * @return
     */
    @GetMapping("/sign/{typeId}")
    public BaseResponseDTO getSign(@PathVariable("typeId") Integer typeId) {
        return BaseResponseDTO.success(AliOssUtils.getOssSign(typeId));
    }
}
