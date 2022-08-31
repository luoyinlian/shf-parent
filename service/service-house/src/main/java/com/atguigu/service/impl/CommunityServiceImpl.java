package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Community;
import com.atguigu.mapper.CommunityMapper;
import com.atguigu.service.CommunityService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author luoyin
 * @Date 23:41 2022/8/22
 **/
@Service
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    CommunityMapper communityMapper;
    @Override
    public BaseMapper<Community> getBaseMapper() {
        return communityMapper;
    }
}
