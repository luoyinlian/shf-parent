package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseImage;
import com.atguigu.mapper.HouseImageMapper;
import com.atguigu.service.HouseImageService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author luoyin
 * @Date 18:32 2022/8/23
 **/
@Service
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    HouseImageMapper houseImageMapper;
    @Override
    public BaseMapper<HouseImage> getBaseMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> findHouseImageListByHouseIdAndType(Long HouseId, int type) {
        return houseImageMapper.findHouseImageListByHouseIdAndType(HouseId,type);
    }
}
