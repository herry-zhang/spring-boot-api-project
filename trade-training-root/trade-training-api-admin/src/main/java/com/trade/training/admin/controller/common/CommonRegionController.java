package com.trade.training.admin.controller.common;

import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.service.common.CommonRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author herry-zhang
 * @date 2018-7-16 19:20:38
 */
@RestController
@RequestMapping("/common")
public class CommonRegionController {

    @Autowired
    CommonRegionService commonRegionService;

    /**
     * 获取省市区联动数据
     *
     * @return
     */
    @GetMapping("/region/list")
    public BaseResponseDTO getUserList() {
        return BaseResponseDTO.success(commonRegionService.getRegionList());
    }
}
