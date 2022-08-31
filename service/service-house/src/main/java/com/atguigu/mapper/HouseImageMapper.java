package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageMapper extends BaseMapper<HouseImage> {
    List<HouseImage> findHouseImageListByHouseIdAndType(@Param("houseId") Long houseId, @Param("type") int type);
}
