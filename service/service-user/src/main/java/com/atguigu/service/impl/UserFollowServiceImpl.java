package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author luoyin
 * @Date 10:07 2022/8/25
 **/
@Service
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;
    @Override
    public BaseMapper<UserFollow> getBaseMapper() {
        return userFollowMapper;
    }

    @Override
    public UserFollow getByUserIdAndHouseId(Long userId, Long houseId) {

        return userFollowMapper.getByUserIdAndHouseId(userId,houseId);
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserFollowVo> pageList = userFollowMapper.findListPage(userId);
        return new PageInfo<>(pageList);
    }
}


