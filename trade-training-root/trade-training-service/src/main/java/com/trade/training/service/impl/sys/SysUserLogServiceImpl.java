package com.trade.training.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.trade.training.common.validator.ValidatorUtils;
import com.trade.training.config.SysUserLogTypeCode;
import com.trade.training.mapper.sys.SysUserLogMapper;
import com.trade.training.model.dto.request.BasePageRequestDTO;
import com.trade.training.model.dto.request.sys.SysUserLogSearchRequestDTO;
import com.trade.training.model.dto.response.BasePageResponseListDTO;
import com.trade.training.model.dto.response.BasePageResponsePaginationDTO;
import com.trade.training.model.dto.response.sys.SysUserLogResponseDTO;
import com.trade.training.model.entity.sys.SysUserLog;
import com.trade.training.model.entity.sys.SysUserLogExample;
import com.trade.training.service.sys.SysUserLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author herry-zhang
 * @date 2018-7-16 17:40:42
 */
@Service
public class SysUserLogServiceImpl implements SysUserLogService {

    @Autowired
    private SysUserLogMapper sysUserLogDAO;

    @Override
    public BasePageResponseListDTO<SysUserLogResponseDTO> getUserLogList(BasePageRequestDTO<SysUserLogSearchRequestDTO> basePageRequestDTO) {
        ValidatorUtils.validateEntity(basePageRequestDTO);
        BasePageResponseListDTO<SysUserLogResponseDTO> userLogSearchResponse = new BasePageResponseListDTO<>();
        PageHelper.startPage(basePageRequestDTO.getPageNum(), basePageRequestDTO.getPageSize());
        SysUserLogExample example = new SysUserLogExample();
        example.setOrderByClause("id desc");
        SysUserLogExample.Criteria criteria = example.createCriteria();
        SysUserLogSearchRequestDTO sysLogSearch = basePageRequestDTO.getSearch();
        if(sysLogSearch != null) {
            if (sysLogSearch.getUserId() != null) {
                criteria.andUserIdEqualTo(sysLogSearch.getUserId());
            }
            if (sysLogSearch.getTypeId() != null) {
                criteria.andTypeIdEqualTo(sysLogSearch.getTypeId());
            }
        }
        List<SysUserLog> userLogList = sysUserLogDAO.selectByExample(example);
        List<SysUserLogResponseDTO> userLogResponseList = Lists.transform(userLogList, (SysUserLog sysUserLog) -> {
            SysUserLogResponseDTO sysUserLogResponseDTO = new SysUserLogResponseDTO();
            BeanUtils.copyProperties(sysUserLog, sysUserLogResponseDTO);
            sysUserLogResponseDTO.setType(SysUserLogTypeCode.getByValue(sysUserLog.getTypeId()).getMessage());
            if (sysUserLog.getRelativeId() != null) {
                // 根据不同的类型获取相关ID对应的内容，添加说明
                sysUserLogResponseDTO.setRemark("");
            }
            return sysUserLogResponseDTO;
        });
        BasePageResponsePaginationDTO pagination = new BasePageResponsePaginationDTO();
        pagination.setCurrent(basePageRequestDTO.getPageNum());
        pagination.setPageSize(basePageRequestDTO.getPageSize());
        pagination.setTotal(PageHelper.count(() -> sysUserLogDAO.selectByExample(example)));
        userLogSearchResponse.setPagination(pagination);
        userLogSearchResponse.setList(userLogResponseList);
        return userLogSearchResponse;
    }
}
