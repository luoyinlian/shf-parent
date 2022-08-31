package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService extends BaseService<UserFollow> {

    UserFollow getByUserIdAndHouseId(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);
}
