package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseBroker;
import com.atguigu.mapper.HouseBrokerMapper;
import com.atguigu.service.HouseBrokerService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author luoyin
 * @Date 18:42 2022/8/23
 **/
@Service
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    HouseBrokerMapper houseBrokerMapper;
    @Override
    public BaseMapper<HouseBroker> getBaseMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> findHouseBrokerListByHouseId(Long houseId) {
        return houseBrokerMapper.findHouseBrokerListByHouseId(houseId);
    }
}
