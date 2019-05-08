package com.trade.training.service.impl.common;

import com.trade.training.mapper.common.CommonRegionMapper;
import com.trade.training.model.dto.response.common.CommonRegionResponseDTO;
import com.trade.training.model.dto.response.sys.SysRoleMenuItemResponseDTO;
import com.trade.training.model.entity.cloud.CloudUser;
import com.trade.training.model.entity.cloud.CloudUserExample;
import com.trade.training.model.entity.common.CommonRegion;
import com.trade.training.model.entity.common.CommonRegionExample;
import com.trade.training.service.common.CommonRegionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author herry-zhang
 * @date 2018-9-2 17:05:49
 */
@Service
public class CommonRegionServiceImpl implements CommonRegionService {

    @Autowired
    private CommonRegionMapper commonRegionDAO;

    @Override
    public List<CommonRegionResponseDTO> getRegionList() {
        CommonRegionExample regionExample = new CommonRegionExample();
        regionExample.createCriteria().andParentIdNotEqualTo(0);
        List<CommonRegion> regionList = commonRegionDAO.selectByExample(regionExample);
        List<CommonRegion> firstLevelRegion = regionList.stream()
                .filter((CommonRegion commonRegion) -> commonRegion.getLevelType() == 1)
                .collect(Collectors.toList());
        return getRegionList(firstLevelRegion, regionList);
    }

    private List<CommonRegionResponseDTO> getRegionList(List<CommonRegion> firstLevelRegion, List<CommonRegion> regionList) {
        List<CommonRegionResponseDTO> regionResponseList = new ArrayList<>();
        for (CommonRegion commonRegion : firstLevelRegion) {
            CommonRegionResponseDTO commonRegionResponseDTO = new CommonRegionResponseDTO();
            commonRegionResponseDTO.setValue(commonRegion.getId());
            commonRegionResponseDTO.setLabel(commonRegion.getName());
            List<CommonRegion> secondLevelRegion = regionList.stream()
                    .filter((CommonRegion secondCommonRegionItem) -> secondCommonRegionItem.getParentId().equals(commonRegion.getId()))
                    .collect(Collectors.toList());
            List<CommonRegionResponseDTO> childrenRegionList = getRegionList(secondLevelRegion, regionList);
            if (childrenRegionList.size() > 0) {
                commonRegionResponseDTO.setChildren(childrenRegionList);
            }
            regionResponseList.add(commonRegionResponseDTO);
        }
        return regionResponseList;
    }
}
