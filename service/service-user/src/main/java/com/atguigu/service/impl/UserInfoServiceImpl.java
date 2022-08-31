package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserInfo;
import com.atguigu.mapper.UserInfoMapper;
import com.atguigu.service.UserInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author luoyin
 * @Date 21:01 2022/8/24
 **/
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public BaseMapper<UserInfo> getBaseMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoMapper.getByPhone(phone);
    }
}
