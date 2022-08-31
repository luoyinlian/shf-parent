package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseUser;
import com.atguigu.mapper.HouseUserMapper;
import com.atguigu.service.HouseUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author luoyin
 * @Date 18:48 2022/8/23
 **/
@Service
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    HouseUserMapper houseUserMapper;
    @Override
    public BaseMapper<HouseUser> getBaseMapper() {
        return houseUserMapper;
    }

    @Override
    public List<HouseUser> findHouseUserListByHouseId(Long houseId) {
        return houseUserMapper.findHouseUserListByHouseId(houseId);
    }
}
